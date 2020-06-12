package swingy.Views.console;

import java.util.Scanner;

import swingy.App;
import swingy.Controllers.CreateController;
import swingy.Models.Hero;
import swingy.Models.Stats;
import swingy.Utilities.DatabaseText;
import swingy.Views.interfaces.CreateView;

public class consoleCreate implements CreateView {
    private static CreateController controller;
    private static Scanner scanner;
    private static DatabaseText db;
    private Hero hero;

    public consoleCreate() {
        System.out.println("\nCONSOLE CREATE VIEW\n");
        controller = new CreateController(this);
        scanner = App.getScanner();
        db = App.getDatabase();
    };

    public void setup() {
        String[] jobs = {"Big Swordfish Tank", "Big Villain Arsenal", "Healthy Lt Gabriel Cash Coder", "Losing Programmer", "Old Aries Lickable Cat"};
        Stats[] baseStats = {new Stats(jobs[0], 1), new Stats(jobs[1], 1), new Stats(jobs[2], 1), new Stats(jobs[3], 1), new Stats(jobs[4], 1)};
        String name = "";
        String job = "";
        Integer i = 0;
        int classCount = 4;
        if (db.isClassUnlocked()) {
            classCount = 5;
        }
        try {
            System.out.println("What is your name, hero?");
            while(name.length() < 3 || name.length() > 20) {
                name = scanner.nextLine();
                if(name.length() < 3 )
                    System.out.println("A small name for a small hero. Make it longer!");
                else if (name.length() > 20)
                    System.out.println("Trying to overcompensate for something? Pick a shorter name.");
            }
            System.out.println("Choose one of the following classes. [1-"+classCount+"]");
            System.out.println("Option\tClass\t\t\t\tAttack\tDefense\tHit Points");
            System.out.println("1\t"+jobs[0]+"\t\t2\t5\t10");
            System.out.println("2\t"+jobs[1]+"\t\t5\t2\t10");
            System.out.println("3\t"+jobs[2]+"\t3\t3\t21");
            System.out.println("4\t"+jobs[3]+"\t\t1\t1\t1");
            if (classCount == 5) {
                System.out.println("5\t"+jobs[4]+"\t\t11\t11\t42");
            }
            while(i == 0) {
                try {
                    i = Integer.parseInt(scanner.nextLine());
                    if(i < 1 || i > classCount) {
                        i = 0;
                        System.out.println("Invalid selection.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid selection.");
                }
            }
            job = jobs[i - 1];
        }
        catch (Exception e) {
            quit();
        }
        String input;
        boolean run = true;
        while(run) {
            System.out.println("====================================");
            System.out.println("Name: " + name);
            System.out.println("Class: " + job);
            System.out.println("Level: 1");
            System.out.println("Experience: 0");
            System.out.println("Attack: " + baseStats[i - 1].getStat("attack"));
            System.out.println("Defense: " + baseStats[i - 1].getStat("defense"));
            System.out.println("Hit Points: " + baseStats[i - 1].getStat("health"));
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
                    this.hero = new Hero(name, job);
                    if (!name.toLowerCase().equals("admin") && db.insertHero(hero)) {
                        run = false;
                        controller.confirm();
                    } else {
                        System.out.println("A hero by that name is already registered.");
                        controller.cancel();
                    }
                    break;
                case "cancel":
                    run =  false;
                    System.out.println("Hero retired before the quest even began...");
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
        //new guiCreate().setup();
        System.out.println("switched");
    }

    public void confirm() {
        new consoleGame().setup(hero);
    }

    public void cancel() {
        new consoleHome().setup();
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        //exit jframe?
        System.exit(0);
    }
}