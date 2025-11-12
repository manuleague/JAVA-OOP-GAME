package com.Joc.InSearchOfTreasure.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.Joc.InSearchOfTreasure.ManagerResurse.Dale;
import com.Joc.InSearchOfTreasure.ManagerResurse.Map;

public abstract class Entity {

    /**
     * variabile pt dimensiuni, width si height, real_->dimensiunile caracterului
     */
    protected final int width = 16;
    protected final int height = 16;


    /**
     * variabile pt pozitie
     */
    protected int x;
    protected int y;
    protected int x_destinatie;
    protected int y_destinatie;
    protected int liniiDale;
    protected int colDale;

    /**
     * variabile pentru miscare
     */
    protected boolean miscare;
    protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;

    /**
     * atribute
     */
    protected int viteza;

    /**
     * titlemap
     */
    protected Map map;
    protected int DalaSize;
    protected int xmap;
    protected int ymap;

    /**
     * animatie
     */
    protected Animation animation;
    protected int currentAnimation;

    public Entity(Map tm) {
        map = tm;
        DalaSize = map.getDaleSize();
        animation = new Animation();
    }


    public void setMapPoz() {
        xmap = map.getx();
        ymap = map.gety();
    }
    /**
     * Seteaza pozitia in raport cu dimensiunea dalei. Asftel, cu ajutorul acestei functii jom pozitiona caracterul la mijlocul dalei cu coord i1 si i2
     */
    public void setDalaPoz(int i1, int i2) {
        y = i1 * DalaSize + DalaSize /2;
        x = i2 * DalaSize + DalaSize /2;
        x_destinatie = x;
        y_destinatie = y;
    }

    public void setLeft() {
        if(miscare) return;
        left = true;
        miscare = valideaza_PozUrm();
    }
    public void setRight() {
        if(miscare) return;
        right = true;
        miscare = valideaza_PozUrm();
    }
    public void setUp() {
        if(miscare) return;
        up = true;
        miscare = valideaza_PozUrm();
    }
    public void setDown() {
        if(miscare) return;
        down = true;
        miscare = valideaza_PozUrm();
    }

    /**
     * Se apeleaza atucni cand caracterul se intersecteaza cu comorile
     * @param o
     * @return
     */
    public boolean intersects(Entity o) {
        return getRectangle().intersects(o.getRectangle());
    }

    /**
     * Rectangle este din java AWT.
     * real_hight si real_width reprezinta dimensiunile comorilor in cadrul dalelor dpvd al interactiunii
     * @return
     */
    public Rectangle getRectangle() {

        return new Rectangle(x, y, width, height);
    }

    /**
     * Verifica daca pozitia viitoare este una pe care putem merge,daca da returneaza true
     * in moving care permite mutarea si calculeaza x_destinatie si y_destinatie
     * @return
     */
    public boolean valideaza_PozUrm() {

        if(miscare)
            return true;

        liniiDale = y / DalaSize;
        colDale = x / DalaSize;

        if(left) {
            if(colDale == 0 || map.getTip(liniiDale, colDale - 1) == Dale.BLOCATA) {
                return false;
            }
            else {
                x_destinatie = x - DalaSize;

            }
        }
        else if(right) {
            if(colDale == map.getNrCol() || map.getTip(liniiDale, colDale + 1) == Dale.BLOCATA) {
                return false;
            }
            else {
                x_destinatie = x + DalaSize;

            }
        }
        else if(up) {
            if(liniiDale == 0 || map.getTip(liniiDale - 1, colDale) == Dale.BLOCATA) {
                return false;
            }
            else {
                y_destinatie = y - DalaSize;

            }
        }
        else if(down) {
            if(liniiDale == map.getNrLinii() - 1 || map.getTip(liniiDale + 1, colDale) == Dale.BLOCATA) {
                return false;
            }
            else {
                y_destinatie = y + DalaSize;

            }
        }
        return true;
    }

    /**
     * Calcul destinatie finala daca miscare e true
     */
    public void update() {
        if(miscare)
        {
            if(left && x > x_destinatie)
                x -= viteza;
            else
                left = false;
            if(left && x < x_destinatie)
                x = x_destinatie;
            if(right && x < x_destinatie)
                x += viteza;
            else
                right = false;
            if(right && x > x_destinatie)
                x = x_destinatie;

            if(up && y > y_destinatie)
                y -= viteza;
            else
                up = false;
            if(up && y < y_destinatie)
                y = y_destinatie;
            if(down && y < y_destinatie)
                y += viteza;
            else
                down = false;
            if(down && y > y_destinatie)
                y = y_destinatie;
        }
        /**
         * verificam daca s-a realizat mutarea completa
         */
        if(x == x_destinatie && y == y_destinatie) {
            left = right = up = down = miscare = false;
            liniiDale = y / DalaSize;
            colDale = x / DalaSize;
        }
        animation.update();
    }

    public void draw(Graphics2D g) {
        setMapPoz();
        g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
    }

    public int getx() { return x; }
    public int gety() { return y; }
}
