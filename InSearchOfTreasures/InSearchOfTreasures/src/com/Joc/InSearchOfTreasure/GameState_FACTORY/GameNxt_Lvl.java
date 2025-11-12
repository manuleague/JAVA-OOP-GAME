package com.Joc.InSearchOfTreasure.GameState_FACTORY;


import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Control;
import com.Joc.InSearchOfTreasure.ManagerResurse.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GameNxt_Lvl implements GameState {
    private static int level = 0;
    private BufferedImage bg;
    private BufferedImage comoara;
    private int currentOption = 0;
    /**
     * Optiunile din meniu
     */
    private String[] options = new String[4]; // textul pentru afisarea optiunilor pentru urmatorul nivel
    private int stare = 0;
    protected GameStateManager gsm;
    public GameNxt_Lvl(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public void init() {
        bg = Content.POVESTE[0][0];
        comoara = Content.Comoara[0][0];
        Connection c = null;
        Statement stmt = null;
        int k=0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:limba_menu.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LIMBA_NXT;");
            while (rs.next()) {
                String romana = rs.getString("Romana");
                String engleza = rs.getString("Engleza");
                if(GameMeniu.get_limba()==0) {
                    if(k==0)
                        options[k]=romana+level;
                    else
                        options[k]=romana;
                    k++;
                }
                else
                {
                    if(k==0)
                        options[k]=engleza+level;
                    else
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

        // deseneaz ecranul pentru urmatorul nivel si afiseaza textul corespunzator
        g.drawImage(bg, 0, 0, null);
        if(level<=3)
            Content.scrie(g, options[0], 44, 90);
        Content.scrie(g, options[1], 44, 100);
        Content.scrie(g, options[2], 44, 110);
        Content.scrie(g, options[3], 44, 120);

        if(level<=3)
        {
            if(currentOption == 0) g.drawImage(comoara, 25, 86, null);
        }
        else {
            stare=1;
        }
        if(currentOption == 1) g.drawImage(comoara, 25, 96, null);
        else if(currentOption == 2) g.drawImage(comoara, 25, 106, null);
        else if(currentOption == 3) g.drawImage(comoara, 25, 116, null);
    }

    public void handleInput() {
        // se verifica apasarea tastelor W-sus, S-jos, ENTER...
        if(Control.isPress(Control.S) && currentOption < options.length - 1) {
            Sound.play("menuoption");
            currentOption++;
        }
        if(Control.isPress(Control.W) && currentOption > stare) {
            Sound.play("menuoption");
            currentOption--;
        }
        if(Control.isPress(Control.ENTER)) {
            Sound.play("enter");
            selectOption();
        }
    }

    /**
     * Starile la care sare jocul in functie de lvl.
     */
    public void selectOption() {
        // se schimba starea jocului la nivelul urmator
        if(currentOption == 0) {
            if(level==2)
            {
                gsm.setState(GameStateManager.LVL2);
            }
            else if (level==3)
            {
                gsm.setState(GameStateManager.LVL3);
            }

        }
        // se schimba starea jocului la meniul principal
        if(currentOption==1)
        {
            gsm.setState(GameStateManager.MENIU);
        }
        // se inchide aplicatia
        if(currentOption == 3) {
            System.exit(0);
        }
        // se scimba starea jocului la ecranul de gameover
        if(currentOption==2)
        {
            gsm.setState(GameStateManager.GAMEOVER);
        }
    }

    // seteaza nivelul curent
    public static void setLevel(int x)
    {
        level=x;
    }
}

