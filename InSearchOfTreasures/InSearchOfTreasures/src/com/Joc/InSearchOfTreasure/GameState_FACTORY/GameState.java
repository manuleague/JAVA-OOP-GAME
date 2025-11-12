package com.Joc.InSearchOfTreasure.GameState_FACTORY;

import java.awt.Graphics2D;

/**
 * INTERFATA. Cu ajutorul ei implementam modelul FACTORY. Toate clasele Game..... ,
 * cu exceptia GameFactory care este clasa care instantiaza restul claselor.
 * creeam diferite obiecte ale altor clase
 */
public interface GameState {


    void init();
    void update();
    void draw(Graphics2D g);
    void handleInput();
    void selectOption();

}

