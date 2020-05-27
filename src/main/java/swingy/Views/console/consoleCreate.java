package swingy.Views.console;

import java.util.Scanner;

import swingy.App;
import swingy.Controllers.CreateController;
import swingy.Models.Database;
import swingy.Models.Database2;
import swingy.Models.Hero;
import swingy.Views.gui.guiCreate;
import swingy.Views.interfaces.CreateView;

public class consoleCreate implements CreateView {
    private static CreateController controller;
    private static Scanner scanner;
    private static Database2 db;
    private Hero hero;

    public consoleCreate() {
        System.out.println("CONSOLE CREATE VIEW\n");
        controller = new CreateController(this);
        scanner = App.getScanner();
        db = App.getDatabase();
    };

    public void setup() {
        String[] jobs = {"Big Swordfish Tank", "Big Villain Arsenal", "Healthy Lt Gabriel Cash Coder", "Losing Programmer"};
        String name = "";
        String job = "";
        Integer i = 0;
        try {
            System.out.println("What is your name, hero?");
            while(name.length() < 3 || name.length() > 20) {
                name = scanner.nextLine();
                if(name.length() < 3 )
                    System.out.println("A small name for a small hero. Make it longer!");
                else if (name.length() > 20)
                    System.out.println("Trying to overcompensate for something? Pick a shorter name.");
            }

            System.out.println("Choose one of the following classes. [1-4]");
            System.out.println("Option\tClass\t\t\t\tAttack\tDefense\tHit Points");
            System.out.println("1\t"+jobs[0]+"\t\t2\t5\t10");
            System.out.println("2\t"+jobs[1]+"\t\t5\t2\t10");
            System.out.println("3\t"+jobs[2]+"\t3\t3\t14");
            System.out.println("4\t"+jobs[3]+"\t\t1\t1\t1");
            while(i == 0) {
                
                try {
                    i = Integer.parseInt(scanner.nextLine());
                    if(i < 1 || i > 4) {
                        i = 0;
                        System.out.println("Invalid selection.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid selection.");
                }
            }
            job = jobs[i- 1];
        }
        catch (Exception e) {
            quit();
        }
        String input;
        boolean run = true;

        while(run) {
            System.out.println(name+" the "+job+"? confirm/cancel");
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
        //new guiCreate().setup();
        System.out.println("switched");
    }

    public void confirm() {
        //add hero to database
        db.insertHero(hero);
        //new consoleGame().setup(hero);
        System.out.println("confirmed");
    }

    public void cancel() {
        System.out.println("Hero retired before the quest even began...");
        new consoleHome().setup();
    }

    public void quit() {
        System.out.println("Sayonara~");
        scanner.close();
        //exit jframe?
        System.exit(0);
    }
}