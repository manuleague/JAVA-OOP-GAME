package com.Joc.InSearchOfTreasure.ManagerResurse;

import java.awt.Graphics2D;

import com.Joc.InSearchOfTreasure.GameState_FACTORY.*;


public class GameStateManager {

    private boolean pauza; // verfica daca jocul este in pauza sau nu
    private static GamePauza pauzaState;
    private GameState[] gameStates; // stochează toate stările jocului
    private int curentState; //  indexul stării curente din array-ul gameStates
    private int StareaPrecedenta; // indexul stării anterioare din gameStates

    /**
     * fabrica implementeaza Design Pattern-ul FACTORY. Variabila fabrica va instantia restul variabilelor.
     */
    private GameFactory fabrica;

    public static final int NR_STATES = 9; // nxumărul total de stări ale jocului
    public static final int LVL2 = 0;
    public static final int MENIU = 1;
    public static final int LVL1 = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEPOVESTE = 4;
    public static final int GAMECREDITS = 5;
    public static final int NXT = 6;
    public static final int LVL3 = 7;
    public static final int SETARI = 8;

    /**
     * initial, jocul se deschide in MENIU si este initializata starea de pauza
     * fabrica instantiaza si obiectul de tip GamePauza, dar avem nevoie de conversie pentru a-l face de tip gamepauza
     * aici se insantiaza si obiectul fabrica
     */

    public GameStateManager() {
        Sound.init();
        pauza = false;
        fabrica = new GameFactory();
        pauzaState = (GamePauza) fabrica.getGame("PAUZA",this);
        gameStates = new GameState[NR_STATES];
        setState(MENIU);
    }

    /**
     * in functie de i-ul primit, vom seta si initializa urmatoarea stare
     */
    public void setState(int i) {
        StareaPrecedenta = curentState;
        gameStates[StareaPrecedenta] = null;
        curentState = i;
        if(i == MENIU) {
            gameStates[i] = fabrica.getGame("MENIU",this);
            gameStates[i].init();
        }
        else if(i == LVL1) {
            gameStates[i] = fabrica.getGame("LVL1",this);
            gameStates[i].init();
        }
        else if(i == GAMEOVER) {
            gameStates[i] = fabrica.getGame("OVER",this);
            gameStates[i].init();
        }
        else if(i == GAMEPOVESTE) {
            gameStates[i] = fabrica.getGame("POVESTE",this);
            gameStates[i].init();
        }
        else if(i == GAMECREDITS) {
            gameStates[i] = fabrica.getGame("CREDITS",this);
            gameStates[i].init();
        }
        else if(i == LVL2) {
            gameStates[i] = fabrica.getGame("LVL2",this);
            gameStates[i].init();
        }
        else if(i == NXT) {
            gameStates[i] = fabrica.getGame("NEXT",this);
            gameStates[i].init();
        }
        else if(i == LVL3) {
            gameStates[i] = fabrica.getGame("LVL3",this);
            gameStates[i].init();
        }
        else if(i == SETARI) {
            gameStates[i] = fabrica.getGame("SETARI",this);
            gameStates[i].init();
        }
    }

    public void setPauza(boolean b) {
        pauza = b;
    }

    public void setPauzaState(GamePauza x){pauzaState = x;}

    public void update() {
        if(pauza)
            pauzaState.update();
        else if(gameStates[curentState] != null)
            gameStates[curentState].update();
    }

    public void draw(Graphics2D g) {
        if(pauza)
            pauzaState.draw(g);
        else if(gameStates[curentState] != null)
            gameStates[curentState].draw(g);
    }
}
