package com.Joc.InSearchOfTreasure.GameState_FACTORY.GameLvl;

import com.Joc.InSearchOfTreasure.Entity.Caracter;
import com.Joc.InSearchOfTreasure.Entity.Item;
import com.Joc.InSearchOfTreasure.Entity.Comoara;
import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameState;
import com.Joc.InSearchOfTreasure.ManagerResurse.Hud;
import com.Joc.InSearchOfTreasure.Main_SINGLETON.GamePanel;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Control;
import com.Joc.InSearchOfTreasure.ManagerResurse.Sound;
import com.Joc.InSearchOfTreasure.ManagerResurse.Map;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * clasa abstracta care este mostentita de toate nivelele
 */
abstract public class GameLevels implements GameState {
    protected Color mycolor = new Color(36, 175, 76);
    protected Caracter caracter; // caracterul jocului
    protected Map map; // un obiect care reprezinta harta jcoului
    protected ArrayList<Comoara> comori_list; // o lista de comori care reprezinta comorile de pe harta
    protected ArrayList<Item> items; // obiectele colectabile de pe harta

    private static int Nivel_salvat = 0; // nivelul salvat anterior
    private static int Nivel_actual = 0;

    /**
     * variabile pentru pozitionare camerei
     */
    protected int xsector;
    protected int ysector;
    protected int sectorSize;

    protected Hud hud; // Heads-Up Display

    /**
     * Evenimentele jocului: starea si progresul jocului
     */
    protected boolean blockInput;
    protected boolean levelStart;
    protected boolean levelFinish;
    protected int levelTicks;

    /**
     * variabile pt afisarea liniilor de inceput
     */
    protected ArrayList<Rectangle> linii_inceput;
    protected GameStateManager gsm;


    public GameLevels(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void init();

    protected abstract void pune_Comori();

    public abstract void update();

    public void draw(Graphics2D g) {

        // deseneaza comrorile pe harata si liniile de inceput de nivel
        map.draw(g);
        caracter.draw(g);
        for (Comoara d : comori_list) {
            d.draw(g);
        }
        for (Item i : items) {
            i.draw(g);
        }
        hud.draw(g);

        g.setColor(mycolor);
        for (Rectangle rectangle : linii_inceput) {
            g.fill(rectangle);
        }
    }

    public void handleInput() {
        if (Control.isPress(Control.ESCAPE)) {
            Sound.stop("music1");
            gsm.setPauza(true);
        }
        if (blockInput) return;
        if (Control.isDown(Control.A)) caracter.setLeft();
        if (Control.isDown(Control.D)) caracter.setRight();
        if (Control.isDown(Control.W)) caracter.setUp();
        if (Control.isDown(Control.S)) caracter.setDown();
        if (Control.isPress(Control.SPACE)) caracter.setAction();
    }

    /**
     * Event start e identic pt orice level. In schimb, event finish depinde de starea urmatoare in care se duce jocul.
     * Initial m-am gandit sa l pun tot aici si sa dau starea urmatoare ca si parametru, dar am zis sa nu ma complic.
     *
     * Acesta vreme de o secunda va desena liniile de inceput ale jocului. levelTicks=60 ptc jocul este in 60 FPS
     */
    protected void LevelStart() {
        levelTicks++;
        if (levelTicks == 1) {
            linii_inceput.clear();
            for (int i = 0; i < 5; i++) {
                linii_inceput.add(new Rectangle(0, i * 32, GamePanel.WIDTH, 32));
            }
        }
        if (levelTicks > 1 && levelTicks < 60) {
            for (int i = 0; i < linii_inceput.size(); i++) {
                Rectangle r = linii_inceput.get(i);
                if (i % 2 == 0) {
                    r.x -= 4;
                } else {
                    r.x += 4;
                }
            }
        }
        if (levelTicks == 61) {
            linii_inceput.clear();
            levelStart = false;
            levelTicks = 0;
        }
    }

    protected abstract void LevelFinish();

    @Override
    public void selectOption() {

    }

    /**
     * incarca niveulul salvat anterior
     */
    public static void incarca_lvl_DB() {
        Connection c = null;
        Statement stmt = null;
        int k = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:save_game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SAVE_GAME;");
            while (rs.next()) {
                int level = rs.getInt("LEVEL_SALVAT");
                GameLevels.Nivel_salvat=level;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void setNivel_salvat(int x)
    {
        GameLevels.Nivel_salvat=x;
    }

    public static void setNivel_actual(int x)
    {
        GameLevels.Nivel_actual=x;
    }

    public static int getNivel_salvat()
    {
        return GameLevels.Nivel_salvat;
    }

    public static int getNivel_actual()
    {
        return GameLevels.Nivel_actual;
    }
}
