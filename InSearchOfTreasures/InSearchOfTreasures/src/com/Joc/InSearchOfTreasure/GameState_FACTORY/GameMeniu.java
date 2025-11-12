package com.Joc.InSearchOfTreasure.GameState_FACTORY;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameLvl.GameLevels;
import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Sound;
import com.Joc.InSearchOfTreasure.ManagerResurse.Control;

public class    GameMeniu implements GameState {

    private static int Limba=1;
    private BufferedImage bg;
    private BufferedImage comoara;
    private int currentOption = 0;
    private String[] options=new String[6];
    protected GameStateManager gsm;

    public GameMeniu(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public void init() {
        bg = Content.MENUBG[0][0];
        comoara = Content.Comoara[0][0];
        GameLevels.incarca_lvl_DB();
        Connection c = null;
        Statement stmt = null;
        int k=0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:limba_menu.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LIMBA_MENU;");
            while (rs.next()) {
                String romana = rs.getString("Romana");
                String engleza = rs.getString("Engleza");
                if(GameMeniu.Limba==0) {
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
        Sound.load("/collect.wav", "enter");
        Sound.load("/menuoption.wav", "menuoption");
    }

    /**
     * Inregistrare evenimente tastatura.
     */
    public void update() {
        handleInput();
    }


     // Se deseneaza meniul. Comoara se muta la optiunea curenta.
    public void draw(Graphics2D g) {
        g.drawImage(bg, 0, 0, null);
        Content.scrie(g, options[0], 44, 80);
        Content.scrie(g, options[1], 44, 90);
        Content.scrie(g, options[2], 44, 100);
        Content.scrie(g, options[3], 44, 110);
        Content.scrie(g, options[4], 44, 120);
        Content.scrie(g, options[5], 44, 130);

        if(currentOption == 0) g.drawImage(comoara, 25, 76, null);
        else if(currentOption == 1) g.drawImage(comoara, 25, 86, null);
        else if(currentOption == 2) g.drawImage(comoara, 25, 96, null);
        else if(currentOption == 3) g.drawImage(comoara, 25, 106, null);
        else if(currentOption == 4) g.drawImage(comoara, 25, 116, null);
        else if(currentOption == 5) g.drawImage(comoara, 25, 126, null);
    }

    public void handleInput() {
        /**
         * verifica apasarile de taste
         */
        if(Control.isPress(Control.S) && currentOption < options.length - 1) {
            Sound.play("menuoption");
            currentOption++;
        }
        if(Control.isPress(Control.W) && currentOption > 0) {
            Sound.play("menuoption");
            currentOption--;
        }
        if(Control.isPress(Control.ENTER)) {
            Sound.play("enter");
            selectOption();
        }
    }

    /**
     * Starile in care sare jocul.
     */
    public void selectOption() {
        if(currentOption == 0) {
            gsm.setState(GameStateManager.LVL1);
        }
        if(currentOption == 1) {
            System.exit(0);
        }
        if(currentOption == 2) {
            gsm.setState(GameStateManager.GAMEPOVESTE);
        }
        if(currentOption == 3) {
            gsm.setState(GameStateManager.GAMECREDITS);
        }
        if(currentOption == 4) {
            gsm.setState(GameStateManager.SETARI);
        }
        if(currentOption == 5) {
            if(GameLevels.getNivel_salvat()==1)
                gsm.setState(GameStateManager.LVL1);
            if(GameLevels.getNivel_salvat()==2)
                gsm.setState(GameStateManager.LVL2);
            if(GameLevels.getNivel_salvat()==3)
                gsm.setState(GameStateManager.LVL3);
        }
    }

    /**
     * Functie statica care modifica variabila statica LIMBA in clasa GAMESETARI.
     */
    public static void modifica_limba(int value)
    {
        GameMeniu.Limba=value;
    }
    public static int get_limba(){
        return GameMeniu.Limba;
    }

}

