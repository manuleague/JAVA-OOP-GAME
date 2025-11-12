package com.Joc.InSearchOfTreasure.Entity;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.Map;

public class Comoara extends Entity {

    BufferedImage[] Comoara_img;
    private ArrayList<int[]> DaleChanges;

    public Comoara(Map m) {

        super(m);
        Comoara_img = Content.Comoara[0];
        animation.setFrames(Comoara_img);
        DaleChanges = new ArrayList<>();
    }

    /**
     * adaugam un vector de int care precizeza pozitiile pe care colectarea unui Comoara le va schimba
     */
    public void addChange(int[] i) {
        DaleChanges.add(i);
    }
    public ArrayList<int[]> getChanges() {
        return DaleChanges;
    }
    public void update() {
        animation.update();
    }
    public void draw(Graphics2D g) {
        super.draw(g);
    }
}
