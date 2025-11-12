package com.Joc.InSearchOfTreasure.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.Map;

public class Item extends Entity{

    private BufferedImage item_img;
    private int tip;
    public static final int BARCA = 0;
    public static final int CHEIE = 1;


    public Item(Map m) {
        super(m);
        tip = -1;
    }

    /**
     * Extargere iteme din Content
     */
    public void setTip(int i) {
        tip = i;
        if(tip == BARCA) {
            item_img = Content.ITEMS[1][0];
        }
        else if(tip == CHEIE) {
            item_img = Content.ITEMS[1][2];
        }
    }

    /**
     * dupa colectare se apeleaza functiile de colectare in parte
     */
    public void colectat(Caracter p) {
        if(tip == BARCA) {
            p.take_barca();
        }
        if(tip == CHEIE) {
            p.take_cheie();
        }
    }

    public void draw(Graphics2D g) {
        setMapPoz();
        g.drawImage(item_img, x + xmap - width / 2, y + ymap - height / 2, null);
    }
}
