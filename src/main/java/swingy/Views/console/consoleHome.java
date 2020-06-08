package swingy.Views.console;

import java.util.Scanner;

import swingy.App;
import swingy.Controllers.CreateController;
import swingy.Controllers.HomeController;
import swingy.Views.gui.guiHome;
import swingy.Views.interfaces.HomeView;

public class consoleHome implements HomeView {
    private static HomeController controller;
    private static Scanner scanner;

    public consoleHome() {
        System.out.println("CONSOLE HOME VIEW\n");
        controller = new HomeController(this);
        scanner = App.getScanner();
    };

    public void setup() {
        String input;
        boolean run = true;
        while(run) {
            System.out.println("Input your command and press enter. (\"help\" for command list.)");
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
                    // add check to hero if arena unlocked
                    //if unlocked on any hero
                    //controller.arena();
                    //else
                    System.out.println("The arena has not been opened yet.");
                    break;
                case "up,up,down,down,left,right,left,right,b,a":
                    //if no admin hero
                    //add overpowered admin to db
                    //full legendary blue set
                    //level 1000 OALC
                    //OALC unlocked
                    //arena unlocked
                    System.out.println("ADMIN HAS JOINED THE FRAY!");
                    //else
                    //System.out.println("Code has already been activated");
                    break;
                case "gui":
                    run = false;
                    controller.switchMode();
                    break;
                default:
                    System.out.println("baka");
                    break;
            }
        }
    }

    public void help()  {
        System.out.println("Available Commands:\nCreate - Begin a game with a new hero.\nLOAD - Continue with a previously saved hero..\nquit - exit the app.\ngui - switches to gui.\nhelp - what you're seeing right now.");
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
        //exit jframe?
        System.exit(0);
    }
}