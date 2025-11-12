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

/**
 * Aceasta clasa se aseamana foarte mult cu cea de la credits.
 */
public class GamePoveste implements GameState {

    private BufferedImage bg;
    protected GameStateManager gsm; // gestioneaza starile jocului
    private String[]options=new String[11]; // contine textul pentru poveste incarcat din data base()

    // constructor
    public GamePoveste(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public void init() {
        // initializeaza imaginea de fundal a povestii(poveste.gif)
        bg = Content.POVESTE[0][0];
        Connection c = null;
        Statement stmt = null;
        int k=0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:limba_menu.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LIMBA_POVESTE;");
            while (rs.next()) {
                String romana = rs.getString("Romana");
                String engleza = rs.getString("Engleza");
                // in functie de limba selectata afiseaza...
                if(GameMeniu.get_limba()==0) {
                    options[k]=romana;
                    k++;
                }
                else
                {
                    options[k]=engleza;
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
    }

    public void update() {
        handleInput();
    }

    public void draw(Graphics2D g) {
        // deseneaza textul povestii si afiseaza pe anumite coordonate x,y
        g.drawImage(bg, 0, 0, null);
        Content.scrie(g, options[0], 30, 0);
        Content.scrie(g,  options[1], 0, 20);
        Content.scrie(g,  options[2], 0, 30);
        Content.scrie(g,  options[3], 0, 40);
        Content.scrie(g,  options[4], 0, 50);
        Content.scrie(g,  options[5], 0, 60);
        Content.scrie(g,  options[6], 0, 70);
        Content.scrie(g,  options[7], 0, 80);
        Content.scrie(g,  options[8], 0, 90);
        Content.scrie(g,  options[9], 0, 100);
        Content.scrie(g,  options[10], 20, 130);
    }

    public void handleInput() {
        // verifca daca a fost apasata tasta escape pentru a reveni la meniul principal
        if(Control.isPress(Control.ESCAPE)) {
            gsm.setPauza(false);
            gsm.setState(GameStateManager.MENIU);
        }
    }

    @Override
    public void selectOption() {

    }


}
