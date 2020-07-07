package swingy.Views.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import swingy.App;
import swingy.Controllers.GameController;
import swingy.Models.Hero;
import swingy.Models.Map;
import swingy.Models.Stats;
import swingy.Models.Villain;
import swingy.Utilities.DatabaseText;
import swingy.Views.console.consoleGame;
import swingy.Views.interfaces.GameView;

public class guiGame extends JPanel implements GameView {
    private static JFrame window;
    private static GameController controller;
    private static DatabaseText db;
    private Hero hero;
    private Map map;
    private ArrayList<Villain> villains;

    private JButton moveNorth = new JButton("N");
    private JButton moveEast = new JButton("E");
    private JButton moveWest = new JButton("W");
    private JButton moveSouth = new JButton("S");
    private JTextArea compass = new JTextArea();
    private JTextArea heroStats = new JTextArea();
    private JTextArea heroInventory = new JTextArea();
    private JMenu menu = new JMenu("MENU");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem mode = new JMenuItem("Switch to console");
    private JMenuItem help = new JMenuItem("Help");
    private JMenuItem quit = new JMenuItem("Quit");

    public guiGame() {
        controller = new GameController(this);
        db = App.getDatabase();
        if(window == null)
            window = App.getFrame();
        window.setVisible(true);
    };

    public void setup(Hero hero, boolean resetHeroLocation) {
        this.hero = hero;
        map = new Map(hero);
        if (resetHeroLocation) {
            hero.setLocation(map.getMapCenter());
            db.updateHero(hero);
        }
        villains = controller.generateVillains(map, hero);

        moveNorth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.move("north", map, villains);
                postMove();
            }
        });

        moveEast.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.move("east", map, villains);
                postMove();
            }
        });

        moveWest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.move("west", map, villains);
                postMove();
            }
        });

        moveSouth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.move("south", map, villains);
                postMove();
            }
        });

        mode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.switchMode();
            }
        });

        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.help();
            }
        });

        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.quit();
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(5, 5, 5, 5);

        GridBagConstraints gbcE = new GridBagConstraints();
        gbcE.gridwidth = GridBagConstraints.REMAINDER;
        gbcE.insets = new Insets(5, 70, 5, 5);

        this.menu.add(mode);
        this.menu.add(help);
        this.menu.add(quit);
        this.menuBar.add(menu);

        updateHeroDisplays();
        compass.setEditable(false);
        controller.displayClosestExit(hero, map);
        heroStats.setEditable(false);
        heroInventory.setEditable(false);

        Dimension buttonSize = new Dimension(100, 100);
        moveNorth.setPreferredSize(buttonSize);
        moveEast.setPreferredSize(buttonSize);
        moveWest.setPreferredSize(buttonSize);
        moveSouth.setPreferredSize(buttonSize);
        moveNorth.setMinimumSize(buttonSize);
        moveEast.setMinimumSize(buttonSize);
        moveWest.setMinimumSize(buttonSize);
        moveSouth.setMinimumSize(buttonSize);
 
        this.add(compass, gbc);
        this.add(heroStats);
        this.add(heroInventory, gbc);
        this.add(moveNorth, gbc);
        this.add(moveWest);
        this.add(moveEast,gbcE);
        this.add(moveSouth, gbc);

        this.setVisible(true);
        window.setContentPane(this);
        window.setJMenuBar(menuBar);
        window.revalidate();
    }

    public void setup(Hero hero, Map map, ArrayList<Villain> villains) {
        this.map = map;
        this.villains = villains;
        setup(hero, false);
    }

    private void postMove() {
        if (!hero.getStatus()) {
            controller.gameLost();
        }
        controller.displayClosestExit(hero, map);
    }

    private void updateHeroDisplays() {
        heroStats.setText(
            "Name: "+hero.getName()+
            "\nClass: "+hero.getJob()+
            "\nLevel: "+hero.getLevel()+
            "\nExperience: "+hero.getXP()+
            "\nAttack: "+hero.getStats().getStat("attack")+
            "\nDefense: "+hero.getStats().getStat("defense")+
            "\nHit Points: "+hero.getStats().getStat("health")
        );

        heroInventory.setText(
            "Weapon: "+hero.getInventory().getArtefactName("Weapon")+" (+"+hero.getInventory().getBackpackStats().getStat("attack")+")"+
            "\nArmour: "+hero.getInventory().getArtefactName("Armour")+" (+"+hero.getInventory().getBackpackStats().getStat("defense")+")"+
            "\nHelm: "+hero.getInventory().getArtefactName("Helm")+" (+"+hero.getInventory().getBackpackStats().getStat("health")+")"
        );
    }

    public void displayXPGain(int xp_gain) {
        JOptionPane.showMessageDialog(window, xp_gain+" xp gained!", "Experience Gained", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayLevelUp() {
        JOptionPane.showMessageDialog(window, hero.getName() + " is now level "+hero.getLevel()+"!", "Level Up!", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayClosestExit(int distance, String direction) {
        compass.setText("The nearest exit is "+distance+" units "+direction+".");
    }

    public void help()  {
        String message = "The hero's stats, inventory and distance from the nearest exit are displayed.\nThe goal is to move to an exit by navigating the four cardinal directions using the buttons.\nOn reaching the goal, the hero is teleported to the middle of the next floor.";

        JOptionPane.showMessageDialog(window, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchMode() {
        if (window != null) {
            window.setVisible(false);
        }
        new consoleGame().setup(hero, map);
    }

    private void clearWindow() {
        menuBar.remove(menu);
        menuBar.revalidate();
        menuBar.repaint();
        window.remove(heroInventory);
        window.remove(heroStats);
        window.remove(moveNorth);
        window.remove(moveEast);
        window.remove(moveWest);
        window.remove(moveSouth);
        window.repaint();
    }

    public void quit() {
        System.exit(0);
    }

    public boolean moveHero(String direction, Map map, ArrayList<Villain> villains) {
        int currentPosition = hero.getLocation();
        int targetPosition;
        switch (direction) {
            case "north":
                targetPosition = currentPosition - map.getMapSize();
                break;
            case "south":
                targetPosition = currentPosition + map.getMapSize();
                break;
            case "east":
                targetPosition = currentPosition + 1;
                break;
            default:
                targetPosition = currentPosition - 1;
                break;
        }

        if (targetPosition < 0 ||
            targetPosition > map.getTileCount() ||
            (targetPosition % map.getMapSize() == 1 && (direction.equals("east"))) ||
            (targetPosition % map.getMapSize() == 0 && (direction.equals("west")))) {
                controller.removeVillains(hero);
                controller.gameWon();
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
                String message;
                String[] options = {"Fight", "Flee"};
                if (currentVillain.getName().length() > 0) {
                    message = "You have encountered "+currentVillain.getName()+", a former "+currentVillain.getJob();
                } else {
                    message = "You have encountered "+currentVillain.getJob();
                }
                int choice = JOptionPane.showOptionDialog(window, message, "Encounter", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                boolean battleWon = false;
                switch(choice) {
                    case 0:
                        battleWon = controller.fight(false, hero, currentVillain);
                        break;
                    default:
                        if (!controller.flee()) {
                            battleWon = controller.fight(true, hero, currentVillain);   
                        } else {
                            JOptionPane.showMessageDialog(window, "Managed to escape!", "Escape", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                }
                if (battleWon) {
                    hero.setLocation(targetPosition);
                    JOptionPane.showMessageDialog(window, "You won the battle!", "Victory", JOptionPane.INFORMATION_MESSAGE);
                }
                db.updateHero(hero);
                updateHeroDisplays();
                return true;
            } else {
                hero.setLocation(targetPosition);
                db.updateHero(hero);
                return true;
            }
        }
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(window, hero.getName() + " has been slain.", "Defeat", JOptionPane.INFORMATION_MESSAGE);
        clearWindow();
        new guiHome().setup();
    }

    public void gameVictory() {
        JOptionPane.showMessageDialog(window, hero.getName() + " has moved on to the next level!", "Floor cleared", JOptionPane.INFORMATION_MESSAGE);
        hero.setWins(hero.getWins() + 1);
        db.updateHero(hero);
        clearWindow();
        new guiGame().setup(hero, true);
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
        updateHeroDisplays();
    }

    private void dragonBalls() {
        String message = "The defeated dragon has acknowledged your skill.\n"+
            "It shall grant you one of the following wishes:\n"+
            "1: Access to the Arena.\n"+
            "2: The power of a new class shall be unlocked.\n"+
            "3: A fallen hero shall be granted life.\n"+
            "4: An increase in your own power.";;
        String[] options = {"1", "2", "3", "4"};
        int choice = JOptionPane.showOptionDialog(window, message, "Dragon Wish", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        ArrayList<Hero> allHeroes = db.getAllHeroes();
        ArrayList<Hero> deadHeroes = new ArrayList<Hero>();
        boolean arenaUnlocked = db.isArenaUnlocked();
        boolean jobUnlocked = db.isClassUnlocked();
        for (Hero establishedHero : allHeroes) {
            if (!establishedHero.getStatus()) {
                deadHeroes.add(establishedHero);
            }
        }
        switch (choice) {
            case 0:
                if (arenaUnlocked) {
                    JOptionPane.showMessageDialog(window, "Access to the Arena has already been granted to all.", "Wish: Arena", JOptionPane.INFORMATION_MESSAGE);
                    dragonBalls();
                } else {
                    JOptionPane.showMessageDialog(window, "The Arena is now open!", "Wish: Arena", JOptionPane.INFORMATION_MESSAGE);
                    hero.activateArena();
                }
                break;
            case 1:
                if (jobUnlocked) {
                    JOptionPane.showMessageDialog(window, "The power of the Old Aries Lickable Cat has already been unlocked.", "Wish: Class", JOptionPane.INFORMATION_MESSAGE);
                    dragonBalls();
                } else {
                    JOptionPane.showMessageDialog(window, "The strength of the Old Aries Lickable Cat has been unlocked!", "Wish: Class", JOptionPane.INFORMATION_MESSAGE);
                    hero.activateOALC();
                }
                break;
            case 2:
                if (deadHeroes.size() == 0) {
                    JOptionPane.showMessageDialog(window, "There are no heroes that need reviving.", "Wish: Life", JOptionPane.INFORMATION_MESSAGE);
                    dragonBalls();
                } else {
                    revivalMenu(deadHeroes);
                }
                break;
            case 3:
                hero.increaseXP(3000, controller);
                break;
            default:
                dragonBalls();
        }
    }

    private void revivalMenu(ArrayList<Hero> deadHeroes) {
        ArrayList<String> deadList = new ArrayList<String>();
        for (Hero hero : deadHeroes) {
            deadList.add(hero.getName());
        }
        int choice = JOptionPane.showOptionDialog(window, "Choose the one to be revived.", "Wish: Life", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, deadList.toArray(), deadList.toArray()[0]);
        if (choice < 0) {
            revivalMenu(deadHeroes);
        } else {
            Hero luckyGuy = deadHeroes.get(choice);
            luckyGuy.setStatus(true);
            db.updateHero(luckyGuy);
            JOptionPane.showMessageDialog(window, luckyGuy.getName()+" has been revived!", "Wish: Life", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void doArtefactDrop(Villain villain, String type, String stat) {
        if (!(villain.getInventory().getArtefactName(type).equals("null"))) {
            if (hero.getInventory().getArtefactName(type).equals("null")) {
                hero.getInventory()
                    .acceptArtefact(
                        type,
                        villain.getInventory().getArtefactName(type)
                );
                JOptionPane.showMessageDialog(window, "Obtained "+hero.getInventory().getArtefactName(type), "Artefact found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int choice = JOptionPane.showConfirmDialog(window,
                    "Replace "+hero.getInventory().getArtefactName(type)+" (+"+hero.getInventory().getBackpackStats().getStat(stat)+") with "+
                    villain.getInventory().getArtefactName(type)+" (+"+villain.getInventory().getBackpackStats().getStat(stat)+")?", "Artefact found", JOptionPane.YES_NO_OPTION);
                switch (choice) {
                    case 0:
                        String preDiscard = "Discarded "+hero.getInventory().getArtefactName(type);
                        hero.getInventory()
                        .acceptArtefact(
                            type,
                            villain.getInventory().getArtefactName(type)
                        );
                        JOptionPane.showMessageDialog(window, preDiscard+"\nObtained "+hero.getInventory().getArtefactName(type), "Artefact found", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}