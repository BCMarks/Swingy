package swingy.Views.gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import swingy.App;

public class guiCreate {
    private static JFrame window;

    public guiCreate() {};

    public static void setup() {
        if(window == null) {
            JFrame window = App.getFrame();

            JButton create = new JButton("NEW HERO");
            JButton back = new JButton("BACK");

            create.setBounds(200, 200, 100, 50);
            back.setBounds(200, 300, 100, 50);
            window.add(create);
            window.add(back);
        }
        App.switchToGUI();
    }

    //Do proper help later
    public static void help()  {
        String message = "THIS IS THE HELP VIEW";

        JOptionPane.showMessageDialog(window, message, "Help", JOptionPane.INFORMATION_MESSAGE);
    }
}