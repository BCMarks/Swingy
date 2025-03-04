package swingy.Views.console;

import java.util.Scanner;

import swingy.App;
import swingy.Controllers.CreateController;
import swingy.Models.Hero;
import swingy.Models.Stats;
import swingy.Utilities.DatabaseHeroes;
import swingy.Views.gui.guiCreate;
import swingy.Views.interfaces.CreateView;

public class consoleCreate implements CreateView {
    private static CreateController controller;
    private static Scanner scanner;
    private static DatabaseHeroes db;
    private Hero hero;
    private String[] jobs = {"Big Swordfish Tank", "Big Villain Arsenal", "Healthy Lt Gabriel Cash Coder", "Losing Programmer", "Old Aries Lickable Cat"};
    private Stats[] baseStats = {new Stats(jobs[0], 1), new Stats(jobs[1], 1), new Stats(jobs[2], 1), new Stats(jobs[3], 1), new Stats(jobs[4], 1)};
        

    public consoleCreate() {
        System.out.println("\nCreate New Hero!");
        controller = new CreateController(this);
        scanner = App.getScanner();
        db = App.getDatabase();
    };

    public void setup() {
        String name = "";
        String selection = "";
        int i = 0;
        int classCount = 4;
        if (db.isClassUnlocked()) {
            classCount = 5;
        }
        try {
            System.out.println("\nWhat is your name, hero?");
            while (!controller.isValidHeroName(name)) {
                name = scanner.nextLine();
                nameCheck(name);
            }
            System.out.println("\nChoose one of the following classes. [1-"+classCount+"]");
            System.out.println("Option\tClass\t\t\t\tAttack\tDefense\tHit Points");
            System.out.println("1\t"+jobs[0]+"\t\t2\t5\t10");
            System.out.println("2\t"+jobs[1]+"\t\t5\t2\t10");
            System.out.println("3\t"+jobs[2]+"\t3\t3\t21");
            System.out.println("4\t"+jobs[3]+"\t\t1\t1\t1");
            if (classCount == 5) {
                System.out.println("5\t"+jobs[4]+"\t\t11\t11\t42");
            }
            while (i == 0) {
                try {
                    try {
                        selection = scanner.nextLine();
                    } catch (Exception e) {
                        quit();
                    }
                    i = Integer.parseInt(selection);
                    if(i < 1 || i > classCount) {
                        i = 0;
                        System.out.println("Invalid selection.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid selection.");
                }
            }
        }
        catch (Exception e) {
            quit();
        }
        createMenu(name, i - 1);
    }

    public void setup(String name, int job) {
        if (controller.isValidHeroName(name)) {
            createMenu(name, job);
        } else {
            nameCheck(name);
            setup();
        }
        
    }

    private void nameCheck(String name) {
        if (name.length() < 3 ) {
            System.out.println("A small name for a small hero. Make it longer!");
        } else if (name.length() > 20) {
            System.out.println("Trying to overcompensate for something? Pick a shorter name.");
        } else if (name.toLowerCase().equals("admin")) {
            System.out.println("That name is reserved. Pick a different one.");
        } else if (controller.nameExists(name)) {
            System.out.println("A hero by that name is already registered.");
        }
        if (!name.matches("^[a-zA-Z0-9]*$")) {
            System.out.println("A valid hero name may only contain alphanumeric characters.");
        }
    }

    private void createMenu(String name, int index) {
        String input;
        boolean run = true;
        while (run) {
            System.out.println("\n====================================");
            System.out.println("Name: " + name);
            System.out.println("Class: " + jobs[index]);
            System.out.println("Level: 1");
            System.out.println("Experience: 0");
            System.out.println("Attack: " + baseStats[index].getStat("attack"));
            System.out.println("Defense: " + baseStats[index].getStat("defense"));
            System.out.println("Hit Points: " + baseStats[index].getStat("health"));
            System.out.println("====================================");
            System.out.println("Is this okay? confirm/cancel");
            try {
                input = scanner.nextLine();
            }
            catch (Exception e) {
                input = "";
                quit();
            }
            switch (input.toLowerCase()) {
                case "help":
                    controller.help();
                    break;
                case "quit":
                    run = false;
                    controller.quit();
                case "confirm":
                    this.hero = new Hero(name, jobs[index]);
                    run = false;
                    controller.confirm();
                    break;
                case "cancel":
                    run =  false;
                    System.out.println("\nHero retired before the quest even began...");
                    controller.cancel();
                    break;
                case "gui":
                    run = false;
                    controller.switchMode(name, index);
                    break;
                default:
                    System.out.println("Invalid Input.");
                    break;
            }
        }
    }

    public void help() {
        String message = "\nAvailable Commands:\nCONFIRM - Begin the game with the newly created hero.\nCANCEL - Discard hero and return to Main Menu.\nQUIT - Exit the app.\nGUI - Switches to gui.\nHELP - What you're seeing right now.";
        System.out.println(message);
    }

    public void switchMode(String name, int job) {
        new guiCreate().setup(name, job);
    }

    public void confirm() {
        db.insertHero(hero);
        new consoleGame().setup(hero, false);
    }

    public void cancel() {
        new consoleHome().setup();
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        System.exit(0);
    }
}