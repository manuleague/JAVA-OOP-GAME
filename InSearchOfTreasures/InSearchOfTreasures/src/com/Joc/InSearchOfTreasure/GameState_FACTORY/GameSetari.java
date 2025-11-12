package com.Joc.InSearchOfTreasure.GameState_FACTORY;


import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Control;
import com.Joc.InSearchOfTreasure.ManagerResurse.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Ca setari, se implementeaza dificultatea si limba jocului. Prin dificultate intelegem un timp mai rapid de GAMEOVER si o dificultate mai mare
 */
public class GameSetari implements GameState {
    private BufferedImage bg; // pentru imaginea de fundal
    private BufferedImage comoara; // pentru imaginea comorii
    private int currentOption = 0;
    private String[] options = {"HARD", "MEDIUM", "LOW","ROMANA","ENGLISH"};
    protected GameStateManager gsm; // un obiect care gestioneaza starile jocului
    private GameFactory fabrica;

    public GameSetari(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public void init() {
        fabrica = new GameFactory();
        bg = Content.MENUBG[0][0]; // incarca imaginea pentru fundal
        comoara = Content.Comoara[0][0]; // incarca imaginea pentru comoara
        Sound.load("/collect.wav", "enter");
        Sound.load("/menuoption.wav", "menuoption");
    }

    public void update() {
        handleInput();
    }//face update la comenzile primite de la tastatura

    public void draw(Graphics2D g) {
        // aici deseneaza meniul
        g.drawImage(bg, 0, 0, null);
        Content.scrie(g, options[0], 44, 60);
        Content.scrie(g, options[1], 44, 70);
        Content.scrie(g, options[2], 44, 80);
        Content.scrie(g, options[3], 44, 120);
        Content.scrie(g, options[4], 44, 130);

        // deseneaza comoara pentru optunea curenta
        if (currentOption == 0) g.drawImage(comoara, 25, 56, null);
        else if (currentOption == 1) g.drawImage(comoara, 25, 66, null);
        else if (currentOption == 2) g.drawImage(comoara, 25, 76, null);
        else if (currentOption == 3) g.drawImage(comoara, 25, 116, null);
        else if (currentOption == 4) g.drawImage(comoara, 25, 126, null);
    }

    public void handleInput() {
        // detecteaza si actualizeaza starile date de la tastatura
        if (Control.isPress(Control.S) && currentOption < options.length - 1) {
            Sound.play("menuoption");
            currentOption++;
        }
        if (Control.isPress(Control.W) && currentOption > 0) {
            Sound.play("menuoption");
            currentOption--;
        }
        if (Control.isPress(Control.ENTER)) {
            Sound.play("enter");
            selectOption();
        }
        if (Control.isPress(Control.ESCAPE)) {
            gsm.setPauza(false);
            gsm.setState(GameStateManager.MENIU);
        }
    }

    public void selectOption() {
        // optiuni pentru meniul SETARI
        if (currentOption == 0) {
            GameOver.setTimp_pt_gameover(1800/2); /**< 2 min pt gameover */
            gsm.setState(GameStateManager.MENIU);
        }
        if (currentOption == 1) {
            GameOver.setTimp_pt_gameover(1800); /**< 4 min pt gameover */
            gsm.setState(GameStateManager.MENIU);
        }
        if (currentOption == 2) {
            GameOver.setTimp_pt_gameover(1800*2); /**< 8 minute pt gameover */
            gsm.setState(GameStateManager.MENIU);
        }
        if (currentOption == 3) {
            GameMeniu.modifica_limba(0); /**< Variabila statica pt Romana */
            gsm.setState(GameStateManager.MENIU);
            gsm.setPauzaState((GamePauza) fabrica.getGame("PAUZA",gsm));
        }
        if (currentOption == 4) {
            GameMeniu.modifica_limba(1); /**< Variabila statica pt Engleza */
            gsm.setState(GameStateManager.MENIU);
            gsm.setPauzaState((GamePauza) fabrica.getGame("PAUZA",gsm));
        }
    }
}





