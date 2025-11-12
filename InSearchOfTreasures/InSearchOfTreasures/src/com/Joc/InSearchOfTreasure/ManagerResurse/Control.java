package com.Joc.InSearchOfTreasure.ManagerResurse;

import java.awt.event.KeyEvent;

public class Control {

    public static final int NUMAR_TASTE = 8;
    public static boolean[] keyState = new boolean[NUMAR_TASTE];
    public static boolean[] KeyState_Prec = new boolean[NUMAR_TASTE];
    public static int W = 0;
    public static int A = 1;
    public static int S = 2;
    public static int D = 3;
    public static int SPACE = 4;
    public static int ENTER = 5;
    public static int ESCAPE = 6;
    public static int M = 7;

    /**
     * se poate controla concomitent si din WASD si din sageti
     */
    public static void seteaza_key(int i, boolean b) {
        if(i == KeyEvent.VK_W ||i== KeyEvent.VK_UP)
            keyState[W] = b;
        else if(i == KeyEvent.VK_A||i== KeyEvent.VK_LEFT)
            keyState[A] = b;
        else if(i == KeyEvent.VK_S||i== KeyEvent.VK_DOWN)
            keyState[S] = b;
        else if(i == KeyEvent.VK_D||i== KeyEvent.VK_RIGHT)
            keyState[D] = b;
        else if(i == KeyEvent.VK_SPACE)
            keyState[SPACE] = b;
        else if(i == KeyEvent.VK_ENTER)
            keyState[ENTER] = b;
        else if(i == KeyEvent.VK_ESCAPE)
            keyState[ESCAPE] = b;
        else if(i == KeyEvent.VK_M)
            keyState[M] = b;
    }

    /**
     * stare precedenta devine starea actuala
     */
    public static void update() {
        System.arraycopy(keyState, 0, KeyState_Prec, 0, NUMAR_TASTE);
    }

    /**
     * se considera apasat atunci cand tasta e 1 si starea ei precedenta 0
     */
    public static boolean isPress(int i) {
        return keyState[i] && !KeyState_Prec[i];
    }
    public static boolean isDown(int i) {
        return keyState[i];
    }

}
