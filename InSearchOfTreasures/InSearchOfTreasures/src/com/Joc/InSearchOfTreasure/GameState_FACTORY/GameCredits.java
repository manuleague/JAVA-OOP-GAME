package com.Joc.InSearchOfTreasure.GameState_FACTORY;

import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Control;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GameCredits implements GameState {

    private BufferedImage bg;
    protected GameStateManager gsm;

    public GameCredits(GameStateManager gsm) {
        this.gsm = gsm;
    }

    /**
     * Initializam fundalul.
     */
    public void init() {
        bg = Content.POVESTE[0][0];
    }

    /**
     * Inregistram evenimentele de la tastatura
     */
    public void update() {
        handleInput();
    }

    /**
     * Scriem pe ecran informatiile din baza de date.
     */
    public void draw(Graphics2D g) {
        Connection c = null;
        Statement stmt = null;
        g.drawImage(bg, 0, 0, null);
        int [] x={35,30,20};
        int [] y={10,100,120};
        int k=0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:limba_menu.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LIMBA_CREDITS;");
            while (rs.next()) {
                String romana = rs.getString("Romana");
                String engleza = rs.getString("Engleza");
                if(GameMeniu.get_limba()==0) {
                    Content.scrie(g, romana, x[k], y[k]);
                    k++;
                }
                else
                {
                    Content.scrie(g, engleza, x[k], y[k]);
                    k++;
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        Content.scrie(g, "Danalache ", 20, 30);
        Content.scrie(g, "Emanuel ", 40, 40);
        Content.scrie(g, "1210B", 45, 60);
    }

    public void handleInput() {
        if(Control.isPress(Control.ESCAPE)) {
            gsm.setPauza(false);
            gsm.setState(GameStateManager.MENIU);
        }
    }

    @Override
    public void selectOption() {

    }

}
