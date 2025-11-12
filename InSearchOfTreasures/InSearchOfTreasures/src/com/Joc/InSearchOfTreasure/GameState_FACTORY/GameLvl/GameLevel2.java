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


/**
 * GameLevel2 are aceiasi fel de implementare ca si GameLevel1. Comentarii identice
 */
public class GameLevel2 extends GameLevels {

    public GameLevel2(GameStateManager gsm) {
        super(gsm);
    }

    public void init() {

        Map.camera=0;
        GameLevels.setNivel_actual(2);
        comori_list = new ArrayList<>();
        items = new ArrayList<>();
        map = new Map(16);
        map.incarca_dale("/testtileset.gif");
        map.incarca_map("/testmap.map");
        caracter = new Caracter(map);
        pune_Comori();
        pune_Items();
        caracter.setDalaPoz(1, 1);
        caracter.setTotalComori(comori_list.size());
        sectorSize = GamePanel.WIDTH;
        xsector = caracter.getx() / sectorSize;
        ysector = caracter.gety() / sectorSize;
        map.setCamera(-xsector * sectorSize, -ysector * sectorSize);
        hud = new Hud(caracter, comori_list);

        Sound.load("/bgmusic.wav", "music1");
        Sound.setVolume("music1", -10);
        Sound.loop("music1", 1000, 1000, Sound.getFrames("music1") - 1000);
        Sound.load("/finish.wav", "finish");
        Sound.setVolume("finish", -10);
        Sound.load("/collect.wav", "collect");
        Sound.load("/mapmove.wav", "mapmove");
        Sound.load("/tilechange.wav", "tilechange");
        Sound.load("/splash.wav", "splash");

        linii_inceput = new ArrayList<>();
        levelStart = true;
        LevelStart();
    }


    protected void pune_Comori() {

        Comoara var_comoara;

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(1, 5);
        var_comoara.addChange(new int[]{1, 6, 1});
        var_comoara.addChange(new int[]{1, 7, 1});
        var_comoara.addChange(new int[]{1, 8, 2});
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(1, 20);
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(5, 22);
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(12, 22);
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(21, 22);
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(16, 14);
        var_comoara.addChange(new int[]{16, 12, 2});
        var_comoara.addChange(new int[]{16, 13, 2});
        var_comoara.addChange(new int[]{17, 12, 2});
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(8, 9);
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(14, 10);
        var_comoara.addChange(new int[]{19, 9, 2});
        var_comoara.addChange(new int[]{20, 9, 2});
        comori_list.add(var_comoara);


        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(13, 4);
        comori_list.add(var_comoara);

        var_comoara = new Comoara(map);
        var_comoara.setDalaPoz(5, 6);
        var_comoara.addChange(new int[]{7, 2, 1});
        var_comoara.addChange(new int[]{7, 3, 1});
        comori_list.add(var_comoara);
    }

    protected void pune_Items() {

        Item item;

        item = new Item(map);
        item.setTip(Item.CHEIE);
        item.setDalaPoz(6, 3);
        items.add(item);

        item = new Item(map);
        item.setTip(Item.BARCA);
        item.setDalaPoz(12,22);
        items.add(item);

    }

    public void update() {
        handleInput();
        if(levelStart)
            LevelStart();
        if(levelFinish)
            LevelFinish();
        if(caracter.getnumar_Comori() == caracter.getTotalComori()||caracter.getTicks()>(GameOver.getTimp_gameover())) {
            {
                levelFinish =true;
                blockInput = true;
            }
        }

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
        for(int i = 0; i < comori_list.size(); i++) {
            Comoara d = comori_list.get(i);
            d.update();
            if(caracter.intersects(d)) {
                comori_list.remove(i);
                i--;
                caracter.collectedComoara();
                Sound.play("collect");
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



    protected void LevelFinish() {
        GameNxt_Lvl.setLevel(3);
        levelTicks++;
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
