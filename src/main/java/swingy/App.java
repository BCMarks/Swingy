package swingy;

import java.util.Scanner;
import swingy.Utilities.DatabaseHeroes;
import javax.swing.*;

import swingy.Views.console.consoleHome;
import swingy.Views.gui.guiHome;

public class App 
{
    private static JFrame window;
    private static Scanner scanner;
    private static DatabaseHeroes db;
    public static void main(String[] args)
    {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.WARNING);
        if (args.length != 1 || (!args[0].toLowerCase().equals("console") && !args[0].toLowerCase().equals("gui"))) {
            System.out.println("Usage: java -jar (console | gui)");
            System.exit(1);
        }
        game(args[0]);
    }

    public static JFrame getFrame() {
        if (window == null) {
            window = new JFrame("SWINGY");
            window.setSize(600, 740);
            window.setVisible(true);
            window.setResizable(false);
            window.setLocationRelativeTo(null);
            window.setAlwaysOnTop(true);
            window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        return window;
    }

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public static DatabaseHeroes getDatabase() {
        if (db == null) {
            db = new DatabaseHeroes();
        }
        return db;
    }

    private static void game(String mode) {
        switch(mode.toLowerCase()) {
            case "gui":
                new guiHome().setup();
                break;
            default:
                new consoleHome().setup();
        }
    }
}