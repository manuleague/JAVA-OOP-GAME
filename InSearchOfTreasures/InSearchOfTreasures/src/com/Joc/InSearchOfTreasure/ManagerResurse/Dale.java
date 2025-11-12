package com.Joc.InSearchOfTreasure.ManagerResurse;

import java.awt.image.BufferedImage;

public class Dale {

    private BufferedImage image;
    private int tip;

    public static final int NORMAL = 0; /**< Prin dala normala putem trece. */
    public static final int BLOCATA = 1; /**< Prin dala blocata nu putem trece. */

    public Dale(BufferedImage image, int tip) {
        this.image = image;
        this.tip = tip;
    }

    public BufferedImage getImage() { return image; }
    public int getTip() { return tip; }

}

