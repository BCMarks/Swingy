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
import swingy.Views.gui.guiGame;
import swingy.Views.interfaces.GameView;

public class consoleGame implements GameView {
    private static GameController controller;
    private static Scanner scanner;
    private static DatabaseText db;
    private Hero hero;
    private Map map;
    private ArrayList<Villain> villains;

    public consoleGame() {
        controller = new GameController(this);
        scanner = App.getScanner();
        db = App.getDatabase();
    };

    public void setup(Hero hero, boolean resetHeroLocation) {
        this.hero = hero;
        boolean run = true;
        String input;
        System.out.println("\n"+hero.getName()+" enters the tower on floor "+(hero.getWins() + 1));
        while (run) {
            if (map == null) {
                map = new Map(hero);
            }
            villains = controller.generateVillains(map, hero);
            boolean activeFloor = true;
            if (resetHeroLocation) {
                hero.setLocation(map.getMapCenter());
                db.updateHero(hero);
                resetHeroLocation = false;
            }
            while (activeFloor) {
                System.out.println("\nInput your command and press enter. (\"help\" for command list.)");
                controller.displayClosestExit(hero, map);
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
                        System.out.println("\n====================================");
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
                        System.out.println("\n====================================");
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
                        System.out.println("Invalid Input.");
                        break;
                }
            }
        }
    }

    public void setup(Hero hero, Map map) {
        this.map = map;
        setup(hero, false);
    }

    public boolean moveHero(String direction, Map map, ArrayList<Villain> villains) {
        int currentPosition = hero.getLocation();
        int targetPosition;
        switch (direction) {
            case "north":
            case "n":
                targetPosition = currentPosition - map.getMapSize();
                break;
            case "south":
            case "s":
                targetPosition = currentPosition + map.getMapSize();
                break;
            case "east":
            case "e":
                targetPosition = currentPosition + 1;
                break;
            case "west":
            case "w":
                targetPosition = currentPosition - 1;
                break;
            default:
                System.out.println("Invalid Input. Choose from [NORTH, SOUTH, EAST, WEST]");
                return true;

        }
        
        if (targetPosition < 0 ||
            targetPosition > map.getTileCount() ||
            (targetPosition % map.getMapSize() == 1 && (direction.equals("east") || direction.equals("e"))) ||
            (targetPosition % map.getMapSize() == 0 && (direction.equals("west") || direction.equals("w")))) {
                controller.gameWon();
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
                    System.out.println("\nYou have encountered "+currentVillain.getName()+", a former "+currentVillain.getJob());
                } else {
                    System.out.println("\nYou have encountered "+currentVillain.getJob());
                }
                boolean battleWon = false;
                boolean awaitingDecision = true;
                String input;
                while (awaitingDecision) {
                    System.out.println("\nInput your command and press enter. (\"help\" for command list.)");
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
                            break;
                        case "fight":
                            battleWon = controller.fight(false, hero, currentVillain);
                            awaitingDecision = false;
                            break;
                        case "flee":
                            if (!controller.flee()) {
                                System.out.println("\nYour cowardice enrages your opponent!");
                                battleWon = controller.fight(true, hero, currentVillain);   
                            } else {
                                System.out.println("\nManaged to escape!");
                            }
                            awaitingDecision = false;
                            break;
                        default:
                            System.out.println("Invalid Input.");
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
        System.out.println("\n" + hero.getName() + " has been slain.");
        new consoleHome().setup();
    }

    public void gameVictory() {
        System.out.println("\n" + hero.getName() + " has moved on to the next level!");
        hero.setWins(hero.getWins() + 1);
        map = null;
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
        System.out.println("\nThe defeated dragon has acknowledged your skill.");
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
            switch (input) {
                case "1":
                    if (arenaUnlocked) {
                        System.out.println("Access to the Arena has already been granted to all.");
                    } else {
                        System.out.println("\nThe Arena is now open!");
                        hero.activateArena();
                        awaitingDecision = false;
                    }
                    break;
                case "2":
                    if (jobUnlocked) {
                        System.out.println("The power of the Old Aries Lickable Cat has already been unlocked.");
                    } else {
                        System.out.println("\nThe strength of the Old Aries Lickable Cat has been unlocked!");
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
                    hero.increaseXP(3000, controller);
                    awaitingDecision = false;
                    break;
                default:
                    System.out.println("Select a valid wish.");
            }
        } 
    }

    private void revivalMenu(ArrayList<Hero> deadHeroes) {
        System.out.println("\nChoose the one to be revived.");
        int i = 1;
        String selection = "";
        for (Hero hero : deadHeroes) {
            System.out.println(i+": "+hero.getName());
            i++;
        }
        int deadCount = deadHeroes.size();
        i = 0;
        while(i == 0) {
            try {
                try {
                    selection = scanner.nextLine();
                } catch (Exception e) {
                    quit();
                }
                i = Integer.parseInt(selection);
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
        System.out.println("\n" + luckyGuy.getName()+" has been revived!");
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
                    System.out.println("\nReplace "+hero.getInventory().getArtefactName(type)+" (+"+hero.getInventory().getBackpackStats().getStat(stat)+") with "+villain.getInventory().getArtefactName(type)+" (+"+villain.getInventory().getBackpackStats().getStat(stat)+")? (Y/N)");
                    try {
                        input = scanner.nextLine().toLowerCase();
                    }
                    catch (Exception e) {
                        input = "";
                        quit();
                    }
                    switch (input) {
                        case "y":
                        case "yes":
                            System.out.println("\nDiscarded "+hero.getInventory().getArtefactName(type));
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
                            System.out.println("Invalid Input.");
                            break;
                    }
                }
            }
        }
    }

    public void displayXPGain(int xp_gain) {
        System.out.println(xp_gain+" xp gained!");
    }

    public void displayLevelUp() {
        System.out.println("\n" + hero.getName() + " is now level "+hero.getLevel()+"!");
    }

    public void displayClosestExit(int distance, String direction) {
        System.out.println("The nearest exit is "+distance+" units "+direction+".");
    }

    public void help() {
        String message = "\nAvailable Commands:\n\n(Out of Battle):\nNORTH/EAST/WEST/SOUTH - Move in specified direction.\nSTATS - Displays current hero statistics.\nINVENTORY - Displays current hero inventory.\n\n(In Battle):\nFIGHT - Engage the enemy.\nFLEE - Back away from the enemy (50% success).\n\n(Both):\nQUIT - Exit the app.\nGUI - Switches to gui.\nHELP - What you're seeing right now.";
        System.out.println(message);
    }

    public void switchMode() {
        new guiGame().setup(hero, map, villains);
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        System.exit(0);
    }
}