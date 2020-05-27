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
                    break;
                case "load":
                    controller.heroLoad();
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