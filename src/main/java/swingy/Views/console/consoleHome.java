package swingy.Views.console;

import java.util.Scanner;

import swingy.App;
import swingy.Controllers.HomeController;
import swingy.Models.Hero;
import swingy.Utilities.DatabaseHeroes;
import swingy.Views.gui.guiHome;
import swingy.Views.interfaces.HomeView;

public class consoleHome implements HomeView {
    private static HomeController controller;
    private static DatabaseHeroes db;
    private static Scanner scanner;

    public consoleHome() {
        System.out.println("\nWelcome to Swingy!");
        db = App.getDatabase();
        controller = new HomeController(this);
        scanner = App.getScanner();
    };

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
                case "create":
                    controller.heroCreate();
                    run = false;
                    break;
                case "load":
                    controller.heroLoad();
                    run = false;
                    break;
                case "arena":
                    if (db.isArenaUnlocked()) {
                        controller.arena();
                        run = false;
                    } else {
                        System.out.println("The arena has not been opened yet.");
                    }
                    break;
                case "up,up,down,down,left,right,left,right,b,a":
                    if (db.insertHero(
                            new Hero(
                                "ADMIN",
                                "Old Aries Lickable Cat",
                                1000,
                                0,
                                "Legendary Blue Rock",
                                "Legendary Blue Rock",
                                "Legendary Blue Rock",
                                50,
                                true,
                                1,
                                true,
                                true
                            )
                        )
                    ) {
                        System.out.println("ADMIN HAS JOINED THE FRAY!");
                    } else {
                        System.out.println("Code has already been activated");
                    }
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

    public void arena() {
        new consoleArena().setup();
    }
    
    public void help() {
        String arenaHelp = "";
        if (db.isArenaUnlocked()) {
            arenaHelp = "\nARENA - Simulate battles between heroes and villains.";
        }
        String message = "\nAvailable Commands:\nCREATE - Begin a game with a new hero.\nLOAD - Continue with a previously saved hero."+arenaHelp+"\nQUIT - Exit the app.\nGUI - Switches to gui.\nHELP - What you're seeing right now.";
        System.out.println(message);
    }

    public void switchMode() {
        new guiHome().setup();
    }

    public void create() {
        new consoleCreate().setup();
    }

    public void load() {
        new consoleLoad().setup();
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        System.exit(0);
    }
}