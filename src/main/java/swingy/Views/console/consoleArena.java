package swingy.Views.console;

import java.util.Scanner;
import swingy.App;
import swingy.Controllers.ArenaController;
import swingy.Models.Hero;
import swingy.Models.Villain;
import swingy.Utilities.DatabaseHeroes;
import swingy.Views.gui.guiArena;
import swingy.Views.interfaces.ArenaView;

public class consoleArena implements ArenaView {
    private static ArenaController controller;
    private static DatabaseHeroes db;
    private static Scanner scanner;

    public consoleArena() {
        System.out.println("\nEnter the Arena if you are Brave enough!");
        db = App.getDatabase();
        controller = new ArenaController(this);
        scanner = App.getScanner();
    }

    public void setup() {
        String input;
        boolean run = true;
        while(run) {
            System.out.println("\nInput your command and press enter. (\"help\" for command list.)");
            try {
                input = scanner.nextLine();
            }
            catch (Exception e) {
                input = "";
                quit();
            }
            switch(input.toLowerCase()) {
                case "help":
                    controller.help();
                    break;
                case "quit":
                    controller.quit();
                case "enter":
                    controller.begin();
                    run = false;
                    break;
                case "home":
                    controller.home();
                    run = false;
                    break;
                case "gui":
                    run = false;
                    controller.switchMode();
                    break;
                default:
                    System.out.println("Invalid Input.");
                    break;
            }
        }
    }

    public void begin() {
        boolean run = true;
        String input;
        String selection = "";
        while(run) {  
            int playerOne = 0;
            int playerTwo = 0;
            int choiceCount = displayChoices();
            while (playerOne == 0) {
                System.out.println("\nSelect the Attacker:");
                try {
                    try {
                        selection = scanner.nextLine();
                    } catch (Exception e) {
                        quit();
                    }
                    playerOne = Integer.parseInt(selection);
                    if(playerOne < 1 || playerOne > choiceCount) {
                        playerOne = 0;
                        System.out.println("Invalid selection.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid selection.");
                }
            }
            while (playerTwo == 0) {
                System.out.println("\nSelect the Defender:");
                try {
                    try {
                        selection = scanner.nextLine();
                    } catch (Exception e) {
                        quit();
                    }
                    playerTwo = Integer.parseInt(selection);
                    if(playerTwo < 1 || playerTwo > choiceCount) {
                        playerTwo = 0;
                        System.out.println("Invalid selection.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid selection.");
                }
            }
            int playerOneWins = 0;
            for (int i = 0; i < 100; i++) {
                if (playerOne < 5) {
                    if (playerTwo < 5) {
                        playerOneWins += controller.fight(null, null, selectVillain(playerOne), selectVillain(playerTwo));
                    } else {
                        playerOneWins += controller.fight(null, db.getAllHeroes().get(playerTwo - 5), selectVillain(playerOne), null);
                    }
                } else {
                    if (playerTwo < 5) {
                        playerOneWins += controller.fight(db.getAllHeroes().get(playerOne - 5), null, null, selectVillain(playerTwo));
                    } else {
                        playerOneWins += controller.fight(db.getAllHeroes().get(playerOne - 5), db.getAllHeroes().get(playerTwo - 5), null, null);
                    }
                }
            }
            
            System.out.println("\nThe Attacker wins "+playerOneWins+"% of the time.");

            System.out.println("Simulate another battle? Y/N");
            try {
                input = scanner.nextLine();
            }
            catch (Exception e) {
                input = "";
                quit();
            }
            switch (input.toLowerCase()) {
                case "y":
                    break;
                case "n":
                    run = false;
                    controller.home();
                    break;
                default:
                   System.out.println("I'll take that as a no...");
                    run = false;
                    controller.home();
            }
        }
    }

    private Villain selectVillain(int choice) {
        switch (choice) {
            case 1:
                return new Villain("", "Ratticus", "Giant Rat", 1, 0, "null", "null", "null", 0, true);
            case 2:
                return new Villain("", "2Spoopy4Me", "Skeleton Soldier", 1, 0, "null", "null", "null", 0, true);
            case 3:
                return new Villain("", "Tom Riddle", "Banished Sorceror", 1, 0, "null", "null", "null", 0, true);
            default:
                return new Villain("", "Paarthurnax", "Ancient Dragon", 1, 0, "null", "null", "null", 0, true);
        }
    }

    private int displayChoices() {
        int i = 1;
        System.out.println("\n"+i+": Ratticus the Giant Rat");
        i++;
        System.out.println(i+": 2Spoopy4Me the Skeleton Soldier");
        i++;
        System.out.println(i+": Tom Riddle the Banished Sorcerer");
        i++;
        System.out.println(i+": Paarthurnax the Ancient Dragon");
        for (Hero hero : db.getAllHeroes()) {
            i++;
            System.out.println(i+": "+hero.getName()+" the "+hero.getJob());
        }
        return i;
    }

    public void home() {
        new consoleHome().setup();
    }

    public void help() {
        String message = "\nAvailable Commands:\nENTER - Enter the glorious arena.\nHOME - Return to Main Menu.\nQUIT - Exit the app.\nGUI - Switches to gui.\nHELP - What you're seeing right now.";
        System.out.println(message);
    }

    public void switchMode() {
        new guiArena().setup();
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        System.exit(0);
    }
    
}