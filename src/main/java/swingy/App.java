package swingy;

import java.util.Scanner;
import swingy.Utilities.DatabaseText;
import javax.swing.*;
import swingy.Views.console.consoleHome;
import swingy.Views.gui.guiHome;

public class App 
{
    private static JFrame window;
    private static Scanner scanner;
    private static DatabaseText db;
    public static void main(String[] args)
    {
        if (args.length != 1 || (!args[0].toLowerCase().equals("console") && !args[0].toLowerCase().equals("gui"))) {
            System.out.println("Usage: java -jar (console | gui)");
            System.exit(1);
        }
        game(args[0]);
    }

    public static JFrame getFrame() {
        if (window == null) {
            window = new JFrame("SWINGY");
            window.setSize(500, 640);
            window.setLayout(null);
            window.setVisible(true);
            window.setResizable(false);
            window.setLocationRelativeTo(null);
            window.setAlwaysOnTop(true);
            //window.setIconImage(image);
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

    public static DatabaseText getDatabase() {
        if (db == null) {
            db = new DatabaseText();
        }
        return db;
    }

    public static void switchToGUI() {
        if (window != null)
            window.setVisible(true);
    }

    public static void switchToConsole() {
        if (window != null)
            window.setVisible(false);
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