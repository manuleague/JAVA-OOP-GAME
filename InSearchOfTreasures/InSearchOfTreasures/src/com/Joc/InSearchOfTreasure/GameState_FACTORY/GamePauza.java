package com.Joc.InSearchOfTreasure.GameState_FACTORY;


import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameLvl.GameLevels;
import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Sound;
import com.Joc.InSearchOfTreasure.ManagerResurse.Control;

/**
 * Implementeaza starea de pauza a jocului.
 */
public class GamePauza implements GameState {

    protected GameStateManager gsm; // gestioneaza starile jocului
    protected String []options=new String[6]; // sir de string uri care contine textul penru meniul de pauza din database

    public GamePauza(GameStateManager gsm) {
        this.gsm = gsm;
        int k=0;
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:limba_menu.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LIMBA_PAUZA;");
            while (rs.next()) {
                String romana = rs.getString("Romana");
                String engleza = rs.getString("Engleza");
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

    public void init() {}
    public void update() {
        handleInput();
    } // actualizeaza starea jcoului

    public void draw(Graphics2D g) {
        // deseneaza meniul de pauza, citit din baza de date
        Content.scrie(g, options[0], 40, 10);
        Content.scrie(g, options[1], 40, 30);
        Content.scrie(g, options[2], 5, 40);
        Content.scrie(g, options[3]+":ESC", 2, 50+5);
        Content.scrie(g, options[4]+":M", 5, 75+5);
        Content.scrie(g, options[5]+":S", 5, 95+5);
    }

    /**
     * Aici se face si sistemul de save game. Se modifica in baza de date nivelul salvat de jucator.
     */
    public void handleInput() {
        // verifica apasarile de taste
        if(Control.isPress(Control.ESCAPE)) {
            Sound.resumeLoop("music1");
            gsm.setPauza(false);
        }
        if(Control.isPress(Control.M)) {
            gsm.setPauza(false);
            gsm.setState(GameStateManager.MENIU);
        }
        if (Control.isPress(Control.S )) {
            gsm.setPauza(false);
            Sound.resumeLoop("music1");
            GameLevels.setNivel_salvat(GameLevels.getNivel_actual());
            Connection c = null;
            Statement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:save_game.db");
                c.setAutoCommit(false);
                stmt = c.createStatement();
                String sql = "UPDATE SAVE_GAME set LEVEL_SALVAT = "+GameLevels.getNivel_actual()+" where ID=1;";
                stmt.executeUpdate(sql);
                c.commit();
                stmt.close();
                c.close();
            }
            catch (Exception e)
            {
                System.exit(0);
            }
        }
    }

    @Override
    public void selectOption() { }
}

