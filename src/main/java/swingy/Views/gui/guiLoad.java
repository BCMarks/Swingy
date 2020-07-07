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
import swingy.Controllers.LoadController;
import swingy.Models.Hero;
import swingy.Utilities.DatabaseText;
import swingy.Views.console.consoleLoad;
import swingy.Views.interfaces.LoadView;

public class guiLoad extends JPanel implements LoadView {
    private static JFrame window;
    private static LoadController controller;
    private static DatabaseText db;

    private ArrayList<Hero> heroes;
    private JList heroList;
    private JScrollPane listScrollPane;
    private JButton confirm = new JButton("CONFIRM");
    private JButton cancel = new JButton("CANCEL");
    private JMenu menu = new JMenu("MENU");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem mode = new JMenuItem("Switch to console");
    private JMenuItem help = new JMenuItem("Help");
    private JMenuItem quit = new JMenuItem("Quit");

    public guiLoad() {
        controller = new LoadController(this);
        db = App.getDatabase();
        if(window == null)
            window = App.getFrame();
        window.setVisible(true);
        heroes = db.getAllHeroes();
        ArrayList<String> displayedDetails = new ArrayList<String>();
        for (Hero hero : heroes) {
            displayedDetails.add(hero.getName()+", a Level "+hero.getLevel()+" "+hero.getJob());
        }
        heroList = new JList<>(displayedDetails.toArray());
    };

    public void setup() {
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (heroes.get(heroList.getSelectedIndex()).getStatus()) {
                    controller.confirm();
                } else {
                    JOptionPane.showMessageDialog(window, heroes.get(heroList.getSelectedIndex()).getName()+" is dead and nothing will ever bring them back... probably.", "Obituary", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.cancel();
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
        gbc.insets = new Insets(5, 5, 5, 5);

        this.menu.add(mode);
        this.menu.add(help);
        this.menu.add(quit);
        this.menuBar.add(menu);

        heroList.setToolTipText("Select your hero.");
        heroList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (heroList.getSelectedIndex() == -1) {
            heroList.setSelectedIndex(0);
        }
        heroList.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(heroList);
        listScrollPane.setPreferredSize(new Dimension(400, 200));
        listScrollPane.setMinimumSize(new Dimension(150, 150));

        Dimension buttonSize = new Dimension(200, 100);
        confirm.setPreferredSize(buttonSize);
        cancel.setPreferredSize(buttonSize);
        confirm.setMinimumSize(buttonSize);
        cancel.setMinimumSize(buttonSize);

        this.add(listScrollPane, gbc);
        this.add(confirm);
        this.add(cancel, gbc);

        this.setVisible(true);
        window.setContentPane(this);
        window.setJMenuBar(menuBar);
        window.revalidate();

        if (heroes.size() == 0) {
            JOptionPane.showMessageDialog(window, "There are no heroes available.", "No more heroes", JOptionPane.INFORMATION_MESSAGE);
            controller.cancel();
        }
    }

    public void setup(int index) {
        heroList.setSelectedIndex(index);
        setup();
    }

    public void help()  {
        String message = "Select a previously created hero to continue the climb!";

        JOptionPane.showMessageDialog(window, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchMode() {
        if (window != null) {
            window.setVisible(false);
        }
        int index = heroList.getSelectedIndex();
        new consoleLoad().setup(heroes.get(index), index);
    }

    public void confirm() {
        clearWindow();
        new guiGame().setup(heroes.get(heroList.getSelectedIndex()), false);
    }

    public void cancel() {
        clearWindow();
        new guiHome().setup();
    }

    private void clearWindow() {
        menuBar.remove(menu);
        menuBar.revalidate();
        menuBar.repaint();
        window.remove(listScrollPane);
        window.remove(confirm);
        window.remove(cancel);
        window.repaint();
    }

    public void quit() {
        System.exit(0);
    }
}