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

import swingy.App;
import swingy.Controllers.HomeController;
import swingy.Views.console.consoleHome;
import swingy.Views.interfaces.HomeView;

public class guiHome extends JPanel implements HomeView{
    private static JFrame window;
    private static HomeController controller;
    private JButton create = new JButton("NEW HERO");
    private JButton load = new JButton("LOAD HERO");
    private JMenu menu = new JMenu("MENU");
    private JMenuBar menuBar = new JMenuBar();
    private JMenuItem mode = new JMenuItem("Switch to console");
    private JMenuItem help = new JMenuItem("Help");
    private JMenuItem quit = new JMenuItem("Quit");

    public guiHome() {
        controller = new HomeController(this);
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

        this.menu.add(mode);
        this.menu.add(help);
        this.menu.add(quit);
        this.menuBar.add(menu);

        //set size of this panel
        create.setBounds(200, 200, 100, 50);
        load.setBounds(200, 300, 100, 50);

        this.add(create);
        this.add(load);
        this.setVisible(true);
        window.setContentPane(this);
        window.setJMenuBar(menuBar);
        window.revalidate();
    }

    //Do proper help later
    public void help()  {
        String message = "THIS IS THE HELP VIEW";

        JOptionPane.showMessageDialog(window, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchMode() {
        if (window != null)
            window.setVisible(false);
        new consoleHome().setup();
    }

    public void arena() {
        //nothing
    }
    
    public void create() {
        
                    // window.remove(create);
                    // window.remove(load);
                    // guiCharacterCreation.setup();
    }

    public void load() {

    }

    public void quit() {
        //close scanner and jframe?
        System.exit(0);
    }
}