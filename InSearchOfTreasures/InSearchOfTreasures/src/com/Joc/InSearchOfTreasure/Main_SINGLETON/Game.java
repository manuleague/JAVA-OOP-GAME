package com.Joc.InSearchOfTreasure.Main_SINGLETON;
import javax.swing.JFrame;


public class Game {

    public static void main(String[] args) {
        JFrame window = new JFrame("In Search Of Treasures");
        window.add(GamePanel.initializare());
        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
