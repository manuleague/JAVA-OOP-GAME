package com.Joc.InSearchOfTreasure.ManagerResurse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.Joc.InSearchOfTreasure.Entity.Caracter;
import com.Joc.InSearchOfTreasure.Entity.Comoara;
import com.Joc.InSearchOfTreasure.Main_SINGLETON.GamePanel;

public class Hud {

    /**
     * Offsetul fata de y=0 pentru a stabili unde punem bara. Pt offset 128, bara va fi jos.
     */
    private int yoffset;

    private BufferedImage bar;
    private BufferedImage comoara;
    private BufferedImage barca;
    private BufferedImage cheie;
    private Caracter caracter;
    private int nr_comori;
    private Font font;
    private Color textColor;

    public Hud(Caracter p, ArrayList<Comoara> d) {
        caracter = p;
        nr_comori = d.size();
        yoffset = GamePanel.HEIGHT;
        bar = Content.BAR[0][0];
        comoara = Content.Comoara[0][0];
        barca = Content.ITEMS[0][0];
        cheie = Content.ITEMS[0][2];
        font = new Font("Arial", Font.PLAIN, 10);
        textColor = new Color(47, 64, 126);
    }

    public void draw(Graphics2D g) {

        /**
         * Incarca bara din josul jocului.
         */
        g.drawImage(bar, 0, yoffset, null);

        /**
         * Scrie cantitatea de comoari Ramase (R:->ramase).
         */
        g.setColor(textColor);
        g.setFont(font);
        String s = "R:" + (nr_comori - caracter.getnumar_Comori());
        Content.scrie(g, s, 90, yoffset + 3);


        /**
         * Cand se colecteaza un obiect, acesta apare in bara.
         */
        if (caracter.hasBarca())
            g.drawImage(barca, 50, yoffset, null);
        if (caracter.hasCheie())
            g.drawImage(cheie, 62, yoffset, null);

        /**
         * Afisarea timpului
         */
        int minutes = (int) (caracter.getTicks() / 3600);
        int seconds = (int) ((caracter.getTicks() / 60) % 60);
        Content.scrie(g, minutes + ":" + seconds, 3, yoffset + 3);
    }
}

