package com.Joc.InSearchOfTreasure.GameState_FACTORY.GameLvl;

import java.awt.Rectangle;
import java.util.ArrayList;
import com.Joc.InSearchOfTreasure.Entity.Caracter;
import com.Joc.InSearchOfTreasure.Entity.Item;
import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameNxt_Lvl;
import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameOver;
import com.Joc.InSearchOfTreasure.ManagerResurse.Hud;
import com.Joc.InSearchOfTreasure.Main_SINGLETON.GamePanel;
import com.Joc.InSearchOfTreasure.Entity.Comoara;
import com.Joc.InSearchOfTreasure.ManagerResurse.Ceas;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Sound;
import com.Joc.InSearchOfTreasure.ManagerResurse.Map;

public class GameLevel1 extends GameLevels {


    public GameLevel1(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        /**
         * creare lista de comoara
         */
        comori_list = new ArrayList<>();
        items = new ArrayList<>();
        Map.camera=0;
        GameLevels.setNivel_actual(1);

        /**
         * load harta
         */
        map = new Map(16);//16 pixeli
        map.incarca_dale("/testtileset3.gif");
        map.incarca_map("/testmap2.map");
        caracter = new Caracter(map);

        /**
         * populam harta jocului cu comori
         */
        pune_Comori();
        pune_Items();
        /**
         * setare pozitie de inceput si totodata si nr de comori
         */
        caracter.setDalaPoz(1, 1);
        caracter.setTotalComori(comori_list.size());
        /**
         * setare camera la pozitia caracterului
         */
        sectorSize = GamePanel.WIDTH;
        xsector = caracter.getx() / sectorSize;
        ysector = caracter.gety() / sectorSize;
        map.setCamera(-xsector * sectorSize, -ysector * sectorSize);

        /**
         * initializeaza hud ul cu caracterul si comorile
          */
        hud = new Hud(caracter, comori_list);


        /**
         * initializare muzica
         */
        Sound.load("/bgmusic.wav", "music1");
        Sound.setVolume("music1", -10);
        Sound.loop("music1", 1000, 1000, Sound.getFrames("music1") - 1000);
        Sound.load("/finish.wav", "finish");
        Sound.setVolume("finish", -10);
        Sound.load("/collect.wav", "collect");
        Sound.load("/mapmove.wav", "mapmove");
        Sound.load("/tilechange.wav", "tilechange");
        Sound.load("/splash.wav", "splash");

        /**
         * animatii de start sub forma de linii
         */
        linii_inceput = new ArrayList<>();
        levelStart = true;
        LevelStart();
    }

    /**
     * pune_comori adauga comori pe harta si mai apoi se pot adauga si schimbari
     */
    protected void pune_Comori() {

        Comoara var_comoara;

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(7, 1);
        comori_list.add(var_comoara);


        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(13,13);
        comori_list.add(var_comoara);


        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(2,4);
        var_comoara.addChange(new int[]{6, 6, 1});
        var_comoara.addChange(new int[]{7, 6, 1});
        var_comoara.addChange(new int[]{8, 6, 1});
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(11,11);
        var_comoara.addChange(new int[]{9, 9, 1});
        var_comoara.addChange(new int[]{9, 10, 1});
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(5,6);
        comori_list.add(var_comoara);
    }

    protected void pune_Items() { }

    public void update() {
        /**
         * evenimente tastatura
         */
        handleInput();

        /**
         * verificam tastatura
         */
        if(levelStart)
            LevelStart();
        if(levelFinish)
            LevelFinish();
        if(caracter.getnumar_Comori() == caracter.getTotalComori()||caracter.getTicks()>(GameOver.getTimp_gameover())) {
            {
                levelFinish = true;
                blockInput = true;
            }
        }

        /**
         * update camera odata cu deplasarea.
         */
        int xsector_prec = xsector;
        int ysector_prec = ysector;
        xsector = caracter.getx() / sectorSize;
        ysector = caracter.gety() / sectorSize;
        map.setCamera(-xsector * sectorSize, -ysector * sectorSize);
        map.update();

        if(xsector_prec != xsector || ysector_prec != ysector) {
            Sound.play("mapmove");
        }

        if(map.se_misca())
            return;

        caracter.update();

        /**
         * update lista comori
         */
        for(int i = 0; i < comori_list.size(); i++) {
            Comoara d = comori_list.get(i);
            d.update();

            /**
             * daca jucatorul ia comoara, comoara dispare si se actualizeaza dimensiunea listei
             */
            if(caracter.intersects(d)) {
                comori_list.remove(i);
                i--;
                caracter.collectedComoara();
                Sound.play("collect");

                /**
                 * modificari in harta
                 */
                ArrayList<int[]> ali = d.getChanges();
                for(int[] j : ali) {
                    map.setDale(j[0], j[1], j[2]);
                }
                if(ali.size() != 0) {
                    Sound.play("tilechange");
                }
            }
        }
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if(caracter.intersects(item)) {
                items.remove(i);
                i--;
                item.colectat(caracter);
                Sound.play("collect");
            }
        }
    }

    /**
     * liniile create de Rectangle +next state
     */
    protected void LevelFinish() {
        levelTicks++;
        GameNxt_Lvl.setLevel(2);
        if(levelTicks == 1) {
            linii_inceput.clear();
            for(int i = 0; i < 9; i++) {
                if(i % 2 == 0) linii_inceput.add(new Rectangle(-128, i * 32, GamePanel.WIDTH, 32));
                else linii_inceput.add(new Rectangle(128, i * 32, GamePanel.WIDTH, 32));
            }
            Sound.stop("music1");
            Sound.play("finish");
        }
        if(levelTicks > 1) {
            for(int i = 0; i < linii_inceput.size(); i++) {
                Rectangle r = linii_inceput.get(i);
                if(i % 2 == 0) {
                    if(r.x < 0) r.x += 4;
                }
                else {
                    if(r.x > 0) r.x -= 4;
                }
            }
        }
        if(levelTicks > 61) {
            if(!Sound.isPlaying("finish")) {
                Ceas.setCeas(caracter.getTicks());
                gsm.setState(GameStateManager.GAMEOVER);
            }
        }
    }
}
