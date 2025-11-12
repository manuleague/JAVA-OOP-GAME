package com.Joc.InSearchOfTreasure.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.Sound;

import com.Joc.InSearchOfTreasure.ManagerResurse.Map;

public class Caracter extends Entity {
    /**
     * variabile pentru starile caracterului care sunt incarcate cu imaginea lui
     */
    private BufferedImage[] downCaracter;
    private BufferedImage[] leftCaracter;
    private BufferedImage[] rightCaracter;
    private BufferedImage[] upCaracter;
    private BufferedImage[] downCaracterBarca;
    private BufferedImage[] leftCaracterBarca;
    private BufferedImage[] rightCaracterBarca;
    private BufferedImage[] upCaracterBarca;

    /**
     * Starile in care putem regasi caracterul
     */
    private final int DOWN = 0;
    private final int LEFT = 1;
    private final int RIGHT = 2;
    private final int UP = 3;
    private final int DOWNBARCA = 4;
    private final int LEFTBARCA = 5;
    private final int RIGHTBARCA = 6;
    private final int UPBARCA = 7;
    
    /**
     * Elemente din gameplay
     */
    private int numar_Comori;
    private int totalComoara;
    private boolean hasBarca;
    private boolean hasCheie;
    private boolean onApa;
    private long ticks;

    public Caracter(Map m) {
        super(m);
        viteza = 1;
        numar_Comori = 0;

        /**
         * Se preiau din Content imaginile cu fiecare stare a jucatorului
         * Se seteaza in delay de 10, adica intr-o secunta vor fi nr de fps/10 animatii.
         */
        downCaracter = Content.PLAYER[0];
        leftCaracter = Content.PLAYER[1];
        rightCaracter = Content.PLAYER[2];
        upCaracter = Content.PLAYER[3];
        downCaracterBarca = Content.PLAYER[4];
        leftCaracterBarca = Content.PLAYER[5];
        rightCaracterBarca = Content.PLAYER[6];
        upCaracterBarca = Content.PLAYER[7];
        animation.setFrames(downCaracter);
    }

    private void setAnimation(int i, BufferedImage[] bi) {
        currentAnimation = i;
        animation.setFrames(bi);
    }

    public void collectedComoara() {
        numar_Comori++;
    }
    public int getnumar_Comori() {
        return numar_Comori;
    }
    public int getTotalComori() { return totalComoara; }
    public void setTotalComori(int i) {
        totalComoara = i;
    }


    /**
     * in momentul in care playerul ia barca, toate dalele de pe mapa care sunt apa se transforma din apa de tip blocked in apa de tip normal
     * astfel se poate trece prin ea
     */
    public void take_barca() {
        hasBarca = true;
        map.inlocuire(22, 4);
    }

    public void take_cheie() {
        hasCheie = true;
    }
    public boolean hasBarca() {
        return hasBarca;
    }
    public boolean hasCheie() {
        return hasCheie;
    }

    /**
     * update la timp
     */
    public long getTicks() {
        return ticks;
    }

    /**
     * Control caracter; se apeleaza functiile din Entity
     */
    public void setDown() {
        super.setDown();
    }
    public void setLeft() {
        super.setLeft();
    }
    public void setRight() {
        super.setRight();
    }
    public void setUp() {
        super.setUp();
    }

    /**
     * Cheia ne ajuta sa deschidem usi
     * Mai jos:actiuni pt a deschide usa in functie de pozitia caracterului
     */
    public void setAction() {
        if(hasCheie) {
            if((currentAnimation == UP||currentAnimation==UPBARCA) && map.getIndex(liniiDale - 1, colDale) == 21) {
                map.setDale(liniiDale - 1, colDale, 1);
                Sound.play("tilechange");
            }
            if((currentAnimation == DOWN||currentAnimation==DOWNBARCA) && map.getIndex(liniiDale + 1, colDale) == 21) {
                map.setDale(liniiDale + 1, colDale, 1);
                Sound.play("tilechange");
            }
            if((currentAnimation == LEFT||currentAnimation==LEFTBARCA) && map.getIndex(liniiDale, colDale - 1) == 21) {
                map.setDale(liniiDale, colDale - 1, 1);
                Sound.play("tilechange");
            }
            if((currentAnimation == RIGHT||currentAnimation==RIGHTBARCA) && map.getIndex(liniiDale, colDale + 1) == 21) {
                map.setDale(liniiDale, colDale + 1, 1);
                Sound.play("tilechange");
            }
        }
    }

    /**
     * Verificam daca suntem pe lava, iar in functie de acest lucru setam animatia pt toate pozitiile: sus jos stg drt
     */
    public void update() {
        ticks++;
        boolean current = onApa;
        onApa = map.getIndex(y_destinatie / DalaSize, x_destinatie / DalaSize) == 4;
        if(!current && onApa) {
            Sound.play("splash");
        }
        if(down) {
            if(onApa && currentAnimation != DOWNBARCA) {
                setAnimation(DOWNBARCA, downCaracterBarca);
            }
            else if(!onApa && currentAnimation != DOWN) {
                setAnimation(DOWN, downCaracter);
            }
        }
        if(left) {
            if(onApa && currentAnimation != LEFTBARCA) {
                setAnimation(LEFTBARCA, leftCaracterBarca);
            }
            else if(!onApa && currentAnimation != LEFT) {
                setAnimation(LEFT, leftCaracter);
            }
        }
        if(right) {
            if(onApa && currentAnimation != RIGHTBARCA) {
                setAnimation(RIGHTBARCA, rightCaracterBarca);
            }
            else if(!onApa && currentAnimation != RIGHT) {
                setAnimation(RIGHT, rightCaracter);
            }
        }
        if(up) {
            if(onApa && currentAnimation != UPBARCA) {
                setAnimation(UPBARCA, upCaracterBarca);
            }
            else if(!onApa && currentAnimation != UP) {
                setAnimation(UP, upCaracter);
            }
        }
        super.update();
    }

    public void draw(Graphics2D g) {
        super.draw(g);
    }

}