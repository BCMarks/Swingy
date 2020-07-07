package swingy.Views.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import swingy.App;
import swingy.Controllers.ArenaController;
import swingy.Models.Hero;
import swingy.Models.Villain;
import swingy.Utilities.DatabaseText;
import swingy.Views.console.consoleArena;
import swingy.Views.interfaces.ArenaView;

public class guiArena extends JPanel implements ArenaView {
    private static JFrame window;
    private static ArenaController controller;
    private static DatabaseText db;
    
    private ArrayList<Hero> heroes;
    private JList firstList;
    private JList secondList;
    private JScrollPane firstListScrollPane;
    private JScrollPane secondListScrollPane;
    private JButton simulate = new JButton("Simulate");
    private JMenu menu = new JMenu("MENU");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem mode = new JMenuItem("Switch to console");
    private JMenuItem help = new JMenuItem("Help");
    private JMenuItem quit = new JMenuItem("Quit");

    public guiArena() {
        controller = new ArenaController(this);
        db = App.getDatabase();
        if(window == null)
            window = App.getFrame();
        window.setVisible(true);
        heroes = db.getAllHeroes();
        createLists();
        
    };

    private void createLists() {
        ArrayList<String> displayedDetails = new ArrayList<String>();
        displayedDetails.add("Ratticus the Giant Rat");
        displayedDetails.add("2Spoopy4Me the Skeleton Soldier");
        displayedDetails.add("Tom Riddle the Banished Sorcerer");
        displayedDetails.add("Paarthurnax the Ancient Dragon");
        for (Hero hero : heroes) {
            displayedDetails.add(hero.getName()+" the "+hero.getJob());
        }
        firstList = new JList<>(displayedDetails.toArray());
        secondList = new JList<>(displayedDetails.toArray());
    }

    public void setup() {
        simulate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.begin();
            }
        });

        mode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.switchMode();
            }
        });

        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.help();
            }
        });

        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.quit();
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);

        this.menu.add(mode);
        this.menu.add(help);
        this.menu.add(quit);
        this.menuBar.add(menu);

        Dimension buttonSize = new Dimension(200, 100);
        simulate.setPreferredSize(buttonSize);
        simulate.setMinimumSize(buttonSize);

        Dimension panelSize = new Dimension(300, 400);
        firstList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (firstList.getSelectedIndex() == -1) {
            firstList.setSelectedIndex(0);
        }
        firstList.setVisibleRowCount(5);
        firstListScrollPane = new JScrollPane(firstList);
        firstListScrollPane.setPreferredSize(panelSize);
        firstListScrollPane.setMinimumSize(panelSize);

        secondList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (secondList.getSelectedIndex() == -1) {
            secondList.setSelectedIndex(0);
        }
        secondList.setVisibleRowCount(5);
        secondListScrollPane = new JScrollPane(secondList);
        secondListScrollPane.setPreferredSize(panelSize);
        secondListScrollPane.setMinimumSize(panelSize);

        this.add(firstListScrollPane);
        this.add(secondListScrollPane, gbc);
        this.add(simulate, gbc);
        this.setVisible(true);
        window.setContentPane(this);
        window.setJMenuBar(menuBar);
        window.revalidate();
    }
    
    public void begin() {
        int playerOne = firstList.getSelectedIndex();
        int playerTwo = secondList.getSelectedIndex();
        int playerOneWins = 0;
        for (int i = 0; i < 100; i++) {
            if (playerOne < 4) {
                if (playerTwo < 4) {
                    playerOneWins += controller.fight(null, null, selectVillain(playerOne), selectVillain(playerTwo));
                } else {
                    playerOneWins += controller.fight(null, heroes.get(playerTwo - 4), selectVillain(playerOne), null);
                }
            } else {
                if (playerTwo < 4) {
                    playerOneWins += controller.fight(heroes.get(playerOne - 4), null, null, selectVillain(playerTwo));
                } else {
                    playerOneWins += controller.fight(heroes.get(playerOne - 4), heroes.get(playerTwo - 4), null, null);
                }
            }
        }
        
        int choice = JOptionPane.showConfirmDialog(window,"The Attacker wins "+playerOneWins+"% of the time.\nSimulate another battle?", "Arena Result", JOptionPane.YES_NO_OPTION);
        if (choice != 0) {
            controller.home();
        }
    }

    private Villain selectVillain(int choice) {
        switch (choice) {
            case 0:
                return new Villain("", "Ratticus", "Giant Rat", 1, 0, "null", "null", "null", 0, true);
            case 1:
                return new Villain("", "2Spoopy4Me", "Skeleton Soldier", 1, 0, "null", "null", "null", 0, true);
            case 2:
                return new Villain("", "Tom Riddle", "Banished Sorceror", 1, 0, "null", "null", "null", 0, true);
            default:
                return new Villain("", "Paarthurnax", "Ancient Dragon", 1, 0, "null", "null", "null", 0, true);
        }
    }

    public void home() {
        clearWindow();
        new guiHome().setup();
    }

    public void help()  {
        String message = "Select an Attacker (left) and a Defender (right) and simulate a battle between them.";

        JOptionPane.showMessageDialog(window, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchMode() {
        if (window != null)
            window.setVisible(false);
        new consoleArena().setup();
    }

    private void clearWindow() {
        menuBar.remove(menu);
        menuBar.revalidate();
        menuBar.repaint();
        window.remove(simulate);
        window.remove(firstListScrollPane);
        window.remove(secondListScrollPane);
        window.repaint();
    }

    public void quit() {
        //close scanner and jframe?
        System.exit(0);
    }
}