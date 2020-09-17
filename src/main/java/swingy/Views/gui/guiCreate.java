package swingy.Views.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import swingy.App;
import swingy.Controllers.CreateController;
import swingy.Models.Hero;
import swingy.Utilities.DatabaseHeroes;
import swingy.Views.console.consoleCreate;
import swingy.Views.interfaces.CreateView;

public class guiCreate extends JPanel implements CreateView {
    private static JFrame window;
    private static CreateController controller;
    private static DatabaseHeroes db;
    private Hero hero;

    private String[] jobs = {"Big Swordfish Tank", "Big Villain Arsenal", "Healthy Lt Gabriel Cash Coder", "Losing Programmer", "Old Aries Lickable Cat"};
    private JTextField heroName = new JTextField(20);
    private JComboBox<String> heroJob = new JComboBox<>(jobs);
    private JTextArea jobDetails = new JTextArea();
    private JButton confirm = new JButton("CONFIRM");
    private JButton cancel = new JButton("CANCEL");
    private JMenu menu = new JMenu("MENU");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem mode = new JMenuItem("Switch to console");
    private JMenuItem help = new JMenuItem("Help");
    private JMenuItem quit = new JMenuItem("Quit");

    public guiCreate() {
        controller = new CreateController(this);
        db = App.getDatabase();
        if(window == null)
            window = App.getFrame();
        window.setVisible(true);
    };

    public void setup() {
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (controller.isValidHeroName(heroName.getText())) {
                    hero = new Hero(heroName.getText(), heroJob.getSelectedItem().toString());
                    controller.confirm();
                } else {
                    String message = "That name is not valid. Select help from the menu for naming rules";
                    JOptionPane.showMessageDialog(window, message, "Invalid Hero", JOptionPane.INFORMATION_MESSAGE);
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
                controller.switchMode(heroName.getText(), heroJob.getSelectedIndex());
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

        heroName.setToolTipText("Enter your hero name.");
        jobDetails.append("Class\t\tAttack\tDefense\tHit Points\n"+
        "-------------------------------------------------"+
        "-------------------------------------------------\n"+
        jobs[0]+"\t   2\t   5\t  10\n"+
        jobs[1]+"\t   5\t   2\t  10\n"+
        jobs[2]+"\t   2\t   2\t  21\n"+
        jobs[3]+"\t   1\t   1\t   1");

        if (heroJob.getSelectedIndex() == -1) {
            heroJob.setSelectedIndex(0);
        }
        if (!db.isClassUnlocked()) {
            heroJob.removeItemAt(4);
        } else {
            jobDetails.append("\n"+jobs[4]+"\t  10\t  10\t  42");
        }
        jobDetails.setEditable(false);

        Dimension buttonSize = new Dimension(200, 100);
        confirm.setPreferredSize(buttonSize);
        cancel.setPreferredSize(buttonSize);
        confirm.setMinimumSize(buttonSize);
        cancel.setMinimumSize(buttonSize);

        this.add(heroName, gbc);
        this.add(heroJob, gbc);
        this.add(jobDetails, gbc);
        this.add(confirm);
        this.add(cancel, gbc);

        this.setVisible(true);
        window.setContentPane(this);
        window.setJMenuBar(menuBar);
        window.revalidate();
    }

    public void setup(String name, int job) {
        heroName.setText(name);
        heroJob.setSelectedIndex(job);
        setup();
    }

    public void help()  {
        String message = "Create a new hero by entering a name in the text field and selecting a class from the dropdown list.\n\nA valid hero name may only contain alphanumeric characters, must be between 3 and 20 characters long and must be unique.";

        JOptionPane.showMessageDialog(window, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchMode(String name, int job) {
        if (window != null)
            window.setVisible(false);
        new consoleCreate().setup(name, job);
    }

    public void confirm() {
        db.insertHero(hero);
        clearWindow();
        new guiGame().setup(hero, false);
    }

    public void cancel() {
        clearWindow();
        new guiHome().setup();
    }

    private void clearWindow() {
        menuBar.remove(menu);
        menuBar.revalidate();
        menuBar.repaint();
        window.remove(heroName);
        window.remove(heroJob);
        window.remove(jobDetails);
        window.remove(confirm);
        window.remove(cancel);
        window.repaint();
    }

    public void quit() {
        System.exit(0);
    }
}