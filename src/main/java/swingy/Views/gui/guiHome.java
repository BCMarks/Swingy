package swingy.Views.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import swingy.App;
import swingy.Controllers.HomeController;
import swingy.Utilities.DatabaseHeroes;
import swingy.Views.console.consoleHome;
import swingy.Views.interfaces.HomeView;

public class guiHome extends JPanel implements HomeView{
    private static JFrame window;
    private static HomeController controller;
    private static DatabaseHeroes db;
    
    private JButton create = new JButton("NEW HERO");
    private JButton load = new JButton("LOAD HERO");
    private JButton arena = new JButton("ARENA");
    private JMenu menu = new JMenu("MENU");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem mode = new JMenuItem("Switch to console");
    private JMenuItem help = new JMenuItem("Help");
    private JMenuItem quit = new JMenuItem("Quit");

    public guiHome() {
        controller = new HomeController(this);
        db = App.getDatabase();
        if(window == null)
            window = App.getFrame();
        window.setVisible(true);
    };

    public void setup() {
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.heroCreate();
            }
        });

        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.heroLoad();
            }
        });

        arena.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                controller.arena();
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
        gbc.insets = new Insets(50, 50, 50, 50);

        this.menu.add(mode);
        this.menu.add(help);
        this.menu.add(quit);
        this.menuBar.add(menu);

        Dimension buttonSize = new Dimension(200, 100);
        create.setPreferredSize(buttonSize);
        load.setPreferredSize(buttonSize);
        arena.setPreferredSize(buttonSize);
        create.setMinimumSize(buttonSize);
        load.setMinimumSize(buttonSize);
        arena.setMinimumSize(buttonSize);

        this.add(create, gbc);
        this.add(load, gbc);
        if (db.isArenaUnlocked()) {
            this.add(arena, gbc);
        }
        this.setVisible(true);
        window.setContentPane(this);
        window.setJMenuBar(menuBar);
        window.revalidate();
    }

    public void help()  {
        String arenaHelp = "";
        if (db.isArenaUnlocked()) {
            arenaHelp = "\nARENA - Simulate battles between heroes and villains.";
        }
        String message = "CREATE - Begin a game with a new hero.\nLOAD - Continue with a previously saved hero."+arenaHelp;

        JOptionPane.showMessageDialog(window, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchMode() {
        if (window != null)
            window.setVisible(false);
        new consoleHome().setup();
    }

    public void arena() {
        clearWindow();
        new guiArena().setup();
    }
    
    public void create() {
        clearWindow();
        new guiCreate().setup();
    }

    public void load() {
        clearWindow();
        new guiLoad().setup();
    }

    private void clearWindow() {
        menuBar.remove(menu);
        menuBar.revalidate();
        menuBar.repaint();
        window.remove(create);
        window.remove(load);
        if (db.isArenaUnlocked()) {
            window.remove(arena);
        }
        window.repaint();
    }

    public void quit() {
        System.exit(0);
    }
}