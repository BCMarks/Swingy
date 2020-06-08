package swingy.Views.console;

import java.util.ArrayList;
import java.util.Scanner;

import swingy.App;
import swingy.Controllers.LoadController;
import swingy.Models.Hero;
import swingy.Utilities.DatabaseText;
import swingy.Views.interfaces.LoadView;

public class consoleLoad implements LoadView {
    private static LoadController controller;
    private static Scanner scanner;
    private static DatabaseText db;
    private Hero hero;

    public consoleLoad() {
        System.out.println("\nCONSOLE LOAD VIEW\n");
        controller = new LoadController(this);
        scanner = App.getScanner();
        db = App.getDatabase();
    };

    public void setup() {
        ArrayList<Hero> heroes = db.getAllHeroes();
        int i = 0, j = 0;
        System.out.printf("###  %-20s %-29s Level\n", "Name", "Class");
        for (Hero hero : heroes) {
            System.out.printf("%03d: %-20s %-29s %02d\n", i+1, hero.getName(), hero.getJob(), hero.getLevel());
            i++;
        }
        System.out.println("Select your hero. [1-"+i+"]");
        while (j == 0) {
            try {
                j = Integer.parseInt(scanner.nextLine());
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
        Hero selection = heroes.get(j - 1);
    
        String input;
        boolean run = true;

        while(run) {
            System.out.println("====================================");
            System.out.println("Name: " + selection.getName());
            System.out.println("Class: " + selection.getJob());
            System.out.println("Level: " + selection.getLevel());
            System.out.println("Experience: " + selection.getXP());
            System.out.println("Attack: " + selection.getStats().getStat("attack"));
            System.out.println("Defense: " + selection.getStats().getStat("defense"));
            System.out.println("Hit Points: " + selection.getStats().getStat("health"));
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
                    this.hero = selection;
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
                    System.out.println("Invalid input.");
                    break;
            }
        }
    }

    public void help()  {
        System.out.println("Available Commands:\nConfirm - begins game with selected character.\nCancel - cancels selection.\nquit - exit the app.\ngui - switches to gui.\nhelp - what you're seeing right now.");
    }

    public void switchMode() {
        //new guiLoad().setup();
        System.out.println("switched");
    }

    public void confirm() {
        new consoleGame().setup(hero);
    }

    public void cancel() {
        System.out.println("Returning to main menu.");
        new consoleHome().setup();
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        //exit jframe?
        System.exit(0);
    }
}