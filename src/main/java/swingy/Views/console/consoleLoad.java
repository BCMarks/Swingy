package swingy.Views.console;

import java.util.ArrayList;
import java.util.Scanner;

import swingy.App;
import swingy.Controllers.LoadController;
import swingy.Models.Database;
import swingy.Models.Database2;
import swingy.Models.Hero;
import swingy.Views.interfaces.LoadView;

public class consoleLoad implements LoadView {
    private static LoadController controller;
    private static Scanner scanner;
    private static Database2 db;

    public consoleLoad() {
        System.out.println("CONSOLE LOAD VIEW\n");
        controller = new LoadController(this);
        scanner = App.getScanner();
        db = App.getDatabase();
    };

    private Hero[] example() {
        Hero[] heroes = {new Hero("Bradleigh", "Big Swordfish Tank", 7, 942, "Diamond Sword", "Dark Robe", null, 41, true),
                        new Hero("Marchall", "Big Villain Arsenal", 5, 3, "AK-47", "Bulletproof Vest", "Hardhat", 28, true),
                        new Hero("Ameen", "Healthy Lt Gabriel Cash Coder", 10, 420, "Peace Treaty", "Denim Jacket", "Wig", 71, true),
                        new Hero("Ismael", "Losing Programmer", 1, 0, null, null, null, 1, true),
                        new Hero("Peter", "Big Swordfish Tank", 2, 14, null, "Badman Shirt", null, 3, false)};
        return heroes;
    }

    public void setup() {
        //temp examples
        //should load from db
        ArrayList<Hero> heroes = db.getAllHeroes();//example();
        int i = 0, j= 0;
        for (Hero hero : heroes) {
            System.out.printf("%03d: %-20s %-29s %03d\n", i+1, hero.getName(), hero.getJob(), hero.getStats().getStat("attack"));
            i++;
        }
        System.out.println("Select your currrr. [1-"+i+"]");
        while(j == 0) {
            try {
                j = Integer.parseInt(scanner.nextLine());
                if(j < 1 || j > i) {
                    j = 0;
                    System.out.println("Invalid selection.");
                }
                else if(!heroes.get(j-1).getStatus()) {
                    System.out.println(heroes.get(j - 1).getName()+" is dead and nothing will ever bring him back... probably.");
                    j = 0;
                }

            } catch (Exception e) {
                System.out.println("Invalid selection.");
            }
        }
        String name = heroes.get(j - 1).getName();
        String job = heroes.get(j - 1).getJob();
    
        String input;
        boolean run = true;

        while(run) {
            System.out.println(name+" the "+job+"? confirm/cancel");
            //display full details
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
        //new consoleGame().setup(heroes[j - 1]);
        System.out.println("confirmed");
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