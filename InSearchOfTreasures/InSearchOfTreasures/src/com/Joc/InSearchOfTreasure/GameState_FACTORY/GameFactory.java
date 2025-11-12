package com.Joc.InSearchOfTreasure.GameState_FACTORY;

import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameLvl.GameLevel1;
import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameLvl.GameLevel2;
import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameLvl.GameLevel3;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;

public class GameFactory {

    /**
     * In functie de game tipe, se instantiaza tipul de joc.
     * gaemtype reprezinta tipul de joc dorit si gsm este managerul de stari a jocului
     */
    public GameState getGame(String gametype, GameStateManager gsm) {
        if (gametype == null) {
            return null;
        }
        if (gametype.equals("POVESTE")) {
            return new GamePoveste(gsm);
        }
        if (gametype.equalsIgnoreCase("LVL1")) {
            return new GameLevel1(gsm);
        }
        if (gametype.equalsIgnoreCase("LVL2")) {
            return new GameLevel2(gsm);
        }
        if (gametype.equalsIgnoreCase("LVL3")) {
            return new GameLevel3(gsm);
        }
        if (gametype.equalsIgnoreCase("CREDITS")) {
            return new GameCredits(gsm);
        }
        if (gametype.equalsIgnoreCase("NEXT")) {
            return new GameNxt_Lvl(gsm);
        }
        if (gametype.equalsIgnoreCase("OVER")) {
            return new GameOver(gsm);
        }
        if (gametype.equals("MENIU")) {
            return new GameMeniu(gsm);
        }
        if (gametype.equals("SETARI")) {
            return new GameSetari(gsm);
        }
        if (gametype.equals("PAUZA")) {
            return new GamePauza(gsm);
        }
        return null;
    }
}
