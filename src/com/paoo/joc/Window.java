package com.paoo.joc;

import javax.swing.*;

public class Window extends JFrame{

    public Window () {
        setTitle("Epic Home Invasion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel(1280, 720));
        setIgnoreRepaint(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
