package com.Joc.InSearchOfTreasure.Main_SINGLETON;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Control;

/**
 * GamePanel: extinde JPanel, implementeaza Runnable si KeyListener.
 * Aici implementam si modelul SINGLETON care creeaza o singura instata
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    /**
     * Variabile pt fereastra
     */
    public static final int WIDTH = 128;
    public static final int HEIGHT = 128;
    public static final int HEIGHT2 = HEIGHT + 16;
    public static final int SCALE = 3;

    /**
     * Variabile pt Gameloop.
     */
    private Thread thread;
    private boolean running;
    private final int FPS = 60;
    private final int TIME = 1000 / FPS;

    /**
     * Variabile pt draw intr un buffer in memorie
     */
    private BufferedImage image;
    private Graphics2D g;

    /**
     * GSM jocului si variabila statica pt singleton
     */
    private GameStateManager gsm;
    private static GamePanel singleton_var=null;

    //constructor
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT2 * SCALE));
        setFocusable(true);
        requestFocus();
    }

    public static GamePanel initializare(){
        if(singleton_var==null)
            singleton_var=new GamePanel();
        return singleton_var;
    }


    public void addNotify() {
        super.addNotify();
        if(thread == null) {
            addKeyListener(this);
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Run pe thread
     */
    //metoda din Runnable
    public void run() {
        init();
        long start;
        long elapsed;
        long wait;
        /**
         * bucla cat timp running e true
         */
        while(running) {
            start = System.nanoTime();
            update();
            draw();
            draw_ecran();
            elapsed = System.nanoTime() - start;
            wait = TIME - elapsed / 1000000;
            if (wait < 0)
                wait = TIME;
            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT2, 1);
        g = (Graphics2D) image.getGraphics();
        gsm = new GameStateManager();
    }

    private void update() {
        gsm.update();
        Control.update();
    }

    private void draw() {
        gsm.draw(g);
    }

    /**
     * Din buffer pe ecran.
     */
    private void draw_ecran() {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT2 * SCALE, null);
        g2.dispose();
    }

    public void keyTyped(KeyEvent key) {}
    public void keyPressed(KeyEvent key) {
        Control.seteaza_key(key.getKeyCode(), true);
    }
    public void keyReleased(KeyEvent key) {
        Control.seteaza_key(key.getKeyCode(), false);
    }

}

