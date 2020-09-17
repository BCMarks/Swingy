package swingy.Views.console;

import java.util.ArrayList;
import java.util.Scanner;

import swingy.App;
import swingy.Controllers.LoadController;
import swingy.Models.Hero;
import swingy.Utilities.DatabaseHeroes;
import swingy.Views.gui.guiLoad;
import swingy.Views.interfaces.LoadView;

public class consoleLoad implements LoadView {
    private static LoadController controller;
    private static Scanner scanner;
    private static DatabaseHeroes db;
    private Hero hero;
    private int index;

    public consoleLoad() {
        System.out.println("\nLoad Existing Hero!");
        controller = new LoadController(this);
        scanner = App.getScanner();
        db = App.getDatabase();
    };

    public void setup() {
        ArrayList<Hero> heroes = db.getAllHeroes();
        if (heroes.size() > 0) {
            int i = 0, j = 0;
            String selection = "";
            System.out.printf("\n###  %-20s %-29s Level\n", "Name", "Class");
            for (Hero hero : heroes) {
                System.out.printf("%03d: %-20s %-29s %02d\n", i+1, hero.getName(), hero.getJob(), hero.getLevel());
                i++;
            }
            System.out.println("Select your hero. [1-"+i+"]");
            while (j == 0) {
                try {
                    try {
                        selection = scanner.nextLine();
                    } catch (Exception e) {
                        quit();
                    }
                    j = Integer.parseInt(selection);
                    if (j < 1 || j > i) {
                        j = 0;
                        System.out.println("Invalid selection.");
                    }
                    else if(!heroes.get(j - 1).getStatus()) {
                        System.out.println(heroes.get(j - 1).getName()+" is dead and nothing will ever bring them back... probably.");
                        j = 0;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid selection.");
                }
            }
            this.hero = heroes.get(j - 1);
            this.index = j - 1;
            loadConfirmationMenu();
        } else {
            System.out.println("There are no heroes available.");
            controller.cancel();
        }
    }

    public void setup(Hero hero, int index) {
        if (!hero.getStatus()) {
            System.out.println(hero.getName()+" is dead and nothing will ever bring them back... probably.");
            setup();
        } else {
            this.hero = hero;
            this.index = index;
            loadConfirmationMenu();
        }
    }

    private void loadConfirmationMenu() {
        String input;
        boolean run = true;
        while (run) {
            System.out.println("\n====================================");
            System.out.println("Name: " + hero.getName());
            System.out.println("Class: " + hero.getJob());
            System.out.println("Level: " + hero.getLevel());
            System.out.println("Experience: " + hero.getXP());
            System.out.println("Attack: " + hero.getStats().getStat("attack"));
            System.out.println("Defense: " + hero.getStats().getStat("defense"));
            System.out.println("Hit Points: " + hero.getStats().getStat("health"));
            System.out.println("====================================");
            System.out.println("Is this okay? confirm/cancel");
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
                case "confirm":
                    run = false;
                    controller.confirm();
                    break;
                case "cancel":
                    run =  false;
                    controller.cancel();
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

    public void help() {
        String message = "\nAvailable Commands:\nCONFIRM - Begin the game with the selected hero.\nCANCEL - Return to Main Menu.\nQUIT - Exit the app.\nGUI - Switches to gui.\nHELP - What you're seeing right now.";
        System.out.println(message);
    }

    public void switchMode() {
        new guiLoad().setup(index);
    }

    public void confirm() {
        new consoleGame().setup(hero, false);
    }

    public void cancel() {
        System.out.println("\nReturning to Main Menu.");
        new consoleHome().setup();
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        System.exit(0);
    }
}