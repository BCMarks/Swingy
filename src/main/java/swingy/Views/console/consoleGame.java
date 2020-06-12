package swingy.Views.console;

import java.util.ArrayList;
import java.util.Scanner;

import swingy.App;
import swingy.Controllers.GameController;
import swingy.Models.Hero;
import swingy.Models.Map;
import swingy.Models.Stats;
import swingy.Models.Villain;
import swingy.Utilities.DatabaseText;
import swingy.Views.interfaces.GameView;

public class consoleGame implements GameView {
    private static GameController controller;
    private static Scanner scanner;
    private static DatabaseText db;
    private Hero hero;

    public consoleGame() {
        System.out.println("\nCONSOLE GAME VIEW\n");
        controller = new GameController(this);
        scanner = App.getScanner();
        db = App.getDatabase();
    };

    public void setup(Hero hero) {
        this.hero = hero;
        boolean run = true;
        boolean resetHeroLocation = false;
        String input;
        System.out.println(hero.getName() + " is ready to fight!\n");
        while (run) {
            Map map = new Map(hero);
            ArrayList<Villain> villains = controller.generateVillains(map, hero);
            map.mapDetails();
            boolean activeFloor = true;
            if (resetHeroLocation) {
                hero.setLocation(map.getMapCenter());
                db.updateHero(hero);
                resetHeroLocation = false;
            }
            while (activeFloor) {
                System.out.println("Input your command and press enter. (\"help\" for command list.)");
                System.out.println(hero.getName() + " is located at tile " + hero.getLocation());
                try {
                    input = scanner.nextLine().toLowerCase();
                }
                catch (Exception e) {
                    input = "";
                    quit();
                }
                switch(input) {
                    case "help":
                        controller.help();
                        break;
                    case "quit":
                        controller.quit();
                    case "gui":
                        run = false;
                        activeFloor = false;
                        controller.switchMode();
                        break;
                    case "stats":
                        System.out.println("====================================");
                        System.out.println("Name: " + hero.getName());
                        System.out.println("Class: " + hero.getJob());
                        System.out.println("Level: " + hero.getLevel());
                        System.out.println("Experience: " + hero.getXP());
                        System.out.println("Attack: " + hero.getStats().getStat("attack"));
                        System.out.println("Defense: " + hero.getStats().getStat("defense"));
                        System.out.println("Hit Points: " + hero.getStats().getStat("health"));
                        System.out.println("====================================");
                        break;
                    case "inventory":
                        System.out.println("====================================");
                        System.out.println("Weapon: "+hero.getInventory().getArtefactName("Weapon")+" (+"+hero.getInventory().getBackpackStats().getStat("attack")+")");
                        System.out.println("Armour: "+hero.getInventory().getArtefactName("Armour")+" (+"+hero.getInventory().getBackpackStats().getStat("defense")+")");
                        System.out.println("Helm: "+hero.getInventory().getArtefactName("Helm")+" (+"+hero.getInventory().getBackpackStats().getStat("health")+")");
                        System.out.println("====================================");
                        break;
                    case "north":
                    case "n":
                    case "south":
                    case "s":
                    case "east":
                    case "e":
                    case "west":
                    case "w":
                        activeFloor = controller.move(input, map, villains);
                        if (!hero.getStatus()) {
                            controller.gameLost();
                            activeFloor = false;
                            run = false;
                        }
                        if (!activeFloor) {
                            resetHeroLocation = true;
                        }
                        break;
                    default:
                        System.out.println("baka");
                        break;
                }
            }
        }
    }

    public boolean moveHero(String direction, Map map, ArrayList<Villain> villains) {
        int currentPosition = hero.getLocation();
        int targetPosition;
        if (direction.equals("north") || direction.equals("n")) {
            targetPosition = currentPosition - map.getMapSize();
        } else if (direction.equals("south") || direction.equals("s")) {
            targetPosition = currentPosition + map.getMapSize();
        } else if (direction.equals("east") || direction.equals("e")) {
            targetPosition = currentPosition + 1;
        } else if (direction.equals("west") || direction.equals("w")) {
            targetPosition = currentPosition - 1;
        } else {
            System.out.println("Invalid input. Choose from [NORTH, SOUTH, EAST, WEST]");
            return true;
        }

        if (targetPosition < 0 ||
            targetPosition > map.getTileCount() ||
            (targetPosition % map.getMapSize() == 1 && (direction.equals("east") || direction.equals("e"))) ||
            (targetPosition % map.getMapSize() == 0 && (direction.equals("west") || direction.equals("w")))) {
                System.out.println(hero.getName() + " has moved on to the next level!");
                hero.setWins(map.getFloor());
                db.updateHero(hero);
                controller.removeVillains(hero);
                return false;
        } else {
            Villain currentVillain = null;
            for (Villain villain : villains) {
                if (villain.getLocation() == targetPosition && villain.getStatus()) {
                    currentVillain = villain;
                    break;
                }
            }
            if (currentVillain != null) {
                if (currentVillain.getName().length() > 0) {
                    System.out.println("You have encountered "+currentVillain.getName()+", a former "+currentVillain.getJob());
                } else {
                    System.out.println("You have encountered "+currentVillain.getJob());
                }
                boolean battleWon = false;
                boolean awaitingDecision = true;
                String input;
                while (awaitingDecision) {
                    System.out.println("Input your command and press enter. (\"help\" for command list.)");
                    try {
                        input = scanner.nextLine().toLowerCase();
                    }
                    catch (Exception e) {
                        input = "";
                        quit();
                    }
                    switch(input) {
                        case "help":
                            controller.help();
                            break;
                        case "quit":
                            controller.quit();
                        case "gui":
                            awaitingDecision = false;
                            controller.switchMode();
                            break;
                        case "fight":
                            battleWon = controller.fight(false, hero, currentVillain);
                            awaitingDecision = false;
                            break;
                        case "flee":
                            if (!controller.flee()) {
                                System.out.println("Your cowardice enrages your opponent!");
                                battleWon = controller.fight(true, hero, currentVillain);   
                            } else {
                                System.out.println("Managed to escape!");
                            }
                            awaitingDecision = false;
                            break;
                        default:
                            System.out.println("baka");
                            break;
                    }
                }
                if (battleWon) {
                    hero.setLocation(targetPosition);
                }
                db.updateHero(hero);
                return true;
            } else {
                hero.setLocation(targetPosition);
                db.updateHero(hero);
                return true;
            }
        }
    }

    public void gameOver() {
        System.out.println(hero.getName() + " has been slain.");
        new consoleHome().setup();
    }

    public void gameVictory() {
        System.out.println(hero.getName() + " has moved on to the next level!");
        hero.setWins(hero.getWins() + 1);
        db.updateHero(hero);
    }

    public void grantSpoils(Villain villain) {
        double roll = Math.random();
        if (roll > 0.66) {
            doArtefactDrop(villain, "Weapon", "attack");
        } else if (roll > 0.33) {
            doArtefactDrop(villain, "Armour", "defense");
        } else {
            doArtefactDrop(villain, "Helm", "health");
        }
        if (villain.getJob().equals("Ancient Dragon")) {
            dragonBalls();
        }
        hero.setStats(new Stats(hero.getJob(), hero.getLevel(), hero.getInventory().getBackpackStats()));
        db.updateHero(hero);
    }

    private void dragonBalls() {
        System.out.println("The defeated dragon has acknowledged your skill.");
        System.out.println("It shall grant you one of the following wishes:");
        System.out.println("1: Access to the Arena.");
        System.out.println("2: The power of a new class shall be unlocked.");
        System.out.println("3: A fallen hero shall be granted life.");
        System.out.println("4: An increase in your own power.");
        boolean awaitingDecision = true;
        String input;
        ArrayList<Hero> allHeroes = db.getAllHeroes();
        ArrayList<Hero> deadHeroes = new ArrayList<Hero>();
        boolean arenaUnlocked = db.isArenaUnlocked();
        boolean jobUnlocked = db.isClassUnlocked();
        for (Hero establishedHero : allHeroes) {
            if (!establishedHero.getStatus()) {
                deadHeroes.add(establishedHero);
            }
        }
        while (awaitingDecision) {
            try {
                input = scanner.nextLine().toLowerCase();
            }
            catch (Exception e) {
                input = "";
                quit();
            }
            switch(input) {
                case "1":
                    if (arenaUnlocked) {
                        System.out.println("Access to the Arena has already been granted to all.");
                    } else {
                        System.out.println("The Arena is now open!");
                        hero.activateArena();
                        awaitingDecision = false;
                    }
                    break;
                case "2":
                    if (jobUnlocked) {
                        System.out.println("The power of the Old Aries Lickable Cat has already been unlocked.");
                    } else {
                        System.out.println("The strength of the Old Aries Lickable Cat has been unlocked!");
                        hero.activateOALC();
                        awaitingDecision = false;
                    }
                    break;
                case "3":
                    if (deadHeroes.size() == 0) {
                        System.out.println("There are no heroes that need reviving.");
                    } else {
                        revivalMenu(deadHeroes);
                        awaitingDecision = false;
                    }
                    break;
                case "4":
                    hero.increaseXP(3000);
                    awaitingDecision = false;
                    break;
                default:
                    System.out.println("Select a valid wish.");
            }
        } 
    }

    private void revivalMenu(ArrayList<Hero> deadHeroes) {
        System.out.println("Choose the one to be revived.");
        int i = 1;
        for (Hero hero : deadHeroes) {
            System.out.println(i+": "+hero.getName());
            i++;
        }
        int deadCount = deadHeroes.size();
        i = 0;
        while(i == 0) {
            try {
                i = Integer.parseInt(scanner.nextLine());
                if(i < 1 || i > deadCount) {
                    i = 0;
                    System.out.println("Invalid selection.");
                }
            } catch (Exception e) {
                System.out.println("Invalid selection.");
            }
        }
        Hero luckyGuy = deadHeroes.get(i - 1);
        luckyGuy.setStatus(true);
        db.updateHero(luckyGuy);
        System.out.println(luckyGuy.getName()+" has been revived!");
    }

    private void doArtefactDrop(Villain villain, String type, String stat) {
        if (!(villain.getInventory().getArtefactName(type).equals("null"))) {
            if (hero.getInventory().getArtefactName(type).equals("null")) {
                hero.getInventory()
                    .acceptArtefact(
                        type,
                        villain.getInventory().getArtefactName(type)
                );
                System.out.println("Obtained "+hero.getInventory().getArtefactName(type));
            } else {
                boolean awaitingDecision = true;
                String input;
                while (awaitingDecision) {
                    System.out.println("Replace "+hero.getInventory().getArtefactName(type)+" (+"+hero.getInventory().getBackpackStats().getStat(stat)+") with "+villain.getInventory().getArtefactName(type)+" (+"+villain.getInventory().getBackpackStats().getStat(stat)+")? (Y/N)");
                    try {
                        input = scanner.nextLine().toLowerCase();
                    }
                    catch (Exception e) {
                        input = "";
                        quit();
                    }
                    switch(input) {
                        case "y":
                        case "yes":
                            System.out.println("Discarded "+hero.getInventory().getArtefactName(type));
                            hero.getInventory()
                            .acceptArtefact(
                                type,
                                villain.getInventory().getArtefactName(type)
                            );
                            System.out.println("Obtained "+hero.getInventory().getArtefactName(type));
                            awaitingDecision = false;
                            break;
                        case "n":
                        case "no":
                            awaitingDecision = false;
                            break;
                        default:
                            System.out.println("baka");
                            break;
                    }
                }
            }
        }
    }

    public void help()  {
        System.out.println("Available Commands:\nConfirm - begins game with selected character.\nCancel - cancels hero.\nquit - exit the app.\ngui - switches to gui.\nhelp - what you're seeing right now.");
    }

    public void switchMode() {
        //new guiGame().setup();
        System.out.println("switched");
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        //exit jframe?
        System.exit(0);
    }
}