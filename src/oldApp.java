package swingy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import swingy.Models.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.Keymap;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class App 
{
    private static JFrame window;
    private static Scanner scanner;
    private static Database db;
    //static Database db = new Database();
    public static void main( String[] args )
    {
        if (args.length != 1 || (!args[0].toLowerCase().equals("console") && !args[0].toLowerCase().equals("gui"))) {
            System.out.println("Usage: java -jar (console | gui)");
            System.exit(1);
        }
        //Database db = new Database();
        List<Hero> herolist = db.getAllHeroes();
        game(args[0]);
        //for (Hero hero : herolist) {System.out.println(hero.getName()+" is a level "+hero.getLevel()+" "+hero.getJob());}
        //Hero hero = new Hero("Ameen", "Healthy Lt Gabriel Cash Coder");
        //db.insertHero(hero);
        //hero.updateHero();
        //System.out.println(hero.getLevel());
        //db.updateHero(hero);
    }

    private static void game(String mode) {
        //create gui
        switch(mode.toLowerCase()) {
            case "gui":
                //do gui stuff
                break;
            default:
                //do console stuff
        }
        JFrame window = new JFrame("SWINGY");
        JTextArea screen = new JTextArea(
            "\n\nSWINGY\n\nNEW GAME");
        //JTextArea map = new JTextArea();
        
        JButton upButton = new JButton("^");
        JButton downButton = new JButton("v");
        JButton leftButton = new JButton("<");
        JButton rightButton = new JButton(">");
        JButton switchButton = new JButton("<html><pre>CONSOLE<br/> MODE!</pre></html>");

        upButton.setBounds(60, 440, 50, 50);
        upButton.setFont(new FontUIResource("Arial", FontUIResource.BOLD, 28));
        upButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dirClick(evt.getActionCommand());
            }
        });

        downButton.setBounds(60, 540, 50, 50);
        downButton.setFont(new FontUIResource("Arial", FontUIResource.BOLD, 28));
        downButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dirClick(evt.getActionCommand());
            }
        });

        leftButton.setBounds(10, 490, 50, 50);
        leftButton.setFont(new FontUIResource("Arial", FontUIResource.BOLD, 28));
        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dirClick(evt.getActionCommand());
            }
        });

        rightButton.setBounds(111, 490, 50, 50);
        rightButton.setFont(new FontUIResource("Arial", FontUIResource.BOLD, 28));
        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                dirClick(evt.getActionCommand());
            }
        });

        switchButton.setBounds(250, 465, 100, 100);
        switchButton.setFont(new FontUIResource("Arial", FontUIResource.BOLD, 16));
        switchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                modeChange(evt, window);
            }
        });

        screen.setSize(400, 400);
        screen.setLocation(22, 22);
        screen.setBackground(ColorUIResource.lightGray);
        screen.setEditable(false);
        screen.setVisible(true);

        //map.setSize(400, 400);
        
        window.add(screen);
        window.add(upButton);
        window.add(downButton);
        window.add(leftButton);
        window.add(rightButton);
        window.add(switchButton);

        upButton.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent evt) {
                String s;
                switch(evt.getKeyCode()){
                    case 37:
                        s = "<";
                        break;
                    case 38:
                        s = "^";
                        break;
                    case 39:
                        s = ">";
                        break;
                    case 40:
                        s = "v";
                        break;
                    default:
                        s = "";

                }
                dirClick(s);
            }
            public void keyTyped(KeyEvent evt) {

            }
            public void keyReleased(KeyEvent evt) {
                
            }
        });

        leftButton.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent evt) {
                String s;
                switch(evt.getKeyCode()){
                    case 37:
                        s = "<";
                        break;
                    case 38:
                        s = "^";
                        break;
                    case 39:
                        s = ">";
                        break;
                    case 40:
                        s = "v";
                        break;
                    default:
                        s = "";

                }
                dirClick(s);
            }
            
            public void keyTyped(KeyEvent evt) {

            }
            public void keyReleased(KeyEvent evt) {
                
            }
        });

        rightButton.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent evt) {
                String s;
                switch(evt.getKeyCode()){
                    case 37:
                        s = "<";
                        break;
                    case 38:
                        s = "^";
                        break;
                    case 39:
                        s = ">";
                        break;
                    case 40:
                        s = "v";
                        break;
                    default:
                        s = "";

                }
                dirClick(s);
            }
            
            public void keyTyped(KeyEvent evt) {

            }
            public void keyReleased(KeyEvent evt) {
                
            }
        });

        downButton.addKeyListener(new KeyListener(){
            public void keyPressed(KeyEvent evt) {
                String s;
                switch(evt.getKeyCode()){
                    case 37:
                        s = "<";
                        break;
                    case 38:
                        s = "^";
                        break;
                    case 39:
                        s = ">";
                        break;
                    case 40:
                        s = "v";
                        break;
                    default:
                        s = "";

                }
                dirClick(s);
            }
            
            public void keyTyped(KeyEvent evt) {

            }
            public void keyReleased(KeyEvent evt) {
                
            }
        });

        window.setSize(460, 640);
        window.setLayout(null);
        window.setVisible(true);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        //window.setIconImage(image);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static void dirClick(String evt) {
        if (evt.isBlank())
            System.out.println("oho");
        else
            System.out.println(evt);
    }

    private static void modeChange(ActionEvent evt, JFrame window) {
        Scanner scanner = new Scanner(System.in);
        String input;
        window.setVisible(false);
        System.out.println("Console Master Race!");
        boolean run = true;
        while(run) {
            System.out.println("Input your command and press enter. (\"help\" for command list.)");
            input = scanner.nextLine();
            switch(input.toLowerCase()) {
                case "help":
                    System.out.println("Put all commands here.");
                    break;
                case "quit":
                    System.out.println("Terminate the program");
                    scanner.close();
                    break;
                case "north":
                    dirClick("^");
                    break;
                case "south":
                    dirClick("v");
                    break;
                case "west":
                    dirClick("<");
                    break;
                case "east":
                    dirClick(">");
                    break;
                case "gui":
                    window.setVisible(true);
                    run = false;
                    break;
                default:
                    System.out.println("baka");
                    break;
            }
        }
    }
}
