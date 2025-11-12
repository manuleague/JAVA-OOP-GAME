package com.Joc.InSearchOfTreasure.ManagerResurse;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import com.Joc.InSearchOfTreasure.Main_SINGLETON.GamePanel;

public class Map {

    /**
     * pozitie
     */
    private int x;
    private int y;
    private int xdest;
    private int ydest;
    private int viteza_tranzitie;
    private boolean miscare;

    /**
     * dimensiuni
     */
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    /**
     * harta
     */
    private int[][] map;
    private int daleSize;
    private int NrLinii;
    private int NrCol;
    private int width;
    private int height;
    public static int camera=0;

    /**
     * variabile pt dale
     */
    private BufferedImage daleset;
    private int NrDale;
    private Dale[][] dales;

    /**
     * variabile pt desen
     */
    private int LiniiOffset;
    private int ColOffset;
    private int ColDraw;
    private int LiniiDraw;

    public Map(int dimens_dala) {
        this.daleSize = dimens_dala;
        ColDraw = GamePanel.HEIGHT / dimens_dala;
        LiniiDraw = GamePanel.WIDTH / dimens_dala;
        viteza_tranzitie = 5;
    }

    /**
     * 	functia incarca_dale citeste dalele in felul urmator: cele aflate pe randul 0 sunt normale, cele pe randul 1 sunt de tip blocked si nu se poate trece prin ele
     */
    public void incarca_dale(String s) {
        try {
            daleset = ImageIO.read(getClass().getResourceAsStream(s));
            NrDale = daleset.getWidth() / daleSize;
            dales = new Dale[2][NrDale];
            BufferedImage subimage;
            for(int col = 0; col < NrDale; col++) {
                subimage = daleset.getSubimage(col * daleSize, 0, daleSize, daleSize);
                dales[0][col] = new Dale(subimage, Dale.NORMAL);
                subimage = daleset.getSubimage(col * daleSize, daleSize, daleSize, daleSize);
                dales[1][col] = new Dale(subimage, Dale.BLOCATA);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 	incarca harta jocului -> un fisier care in primii doi paramentri are nr de linii si coloane, iar dupa are codificarea pt fiecare dala
     */
    public void incarca_map(String s) {

        try {
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            NrCol = Integer.parseInt(br.readLine());
            NrLinii = Integer.parseInt(br.readLine());
            map = new int[NrLinii][NrCol];
            width = NrCol * daleSize;
            height = NrLinii * daleSize;
            xmin = GamePanel.WIDTH - width;
            xmin = -width;
            xmax = 0;
            ymin = GamePanel.HEIGHT - height;
            ymin = -height;
            ymax = 0;

            String delims = "\\s+";
            for(int linie = 0; linie < NrLinii; linie++) {
                String line = br.readLine();
                String[] citeste = line.split(delims);
                for(int col = 0; col < NrCol; col++) {
                    map[linie][col] = Integer.parseInt(citeste[col]);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public int getDaleSize() {
        return daleSize;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getNrLinii() {
        return NrLinii;
    }

    public int getNrCol() {
        return NrCol;
    }

    /**
     * Vom afla de unde sa extragem imaginile din dale avand in vedere restul impartirii valorii din harta la nr de dale si valoarea impartirii la nr de dale.
     * @param linie
     * @param col
     * @return
     */
    public int getTip(int linie, int col) {
        int var = map[linie][col];
        int l = var / NrDale;
        int c = var % NrDale;
        return dales[l][c].getTip();
    }

    public int getIndex(int linie, int col) {
        return map[linie][col];
    }

    public boolean se_misca() {
        return miscare;
    }

    public void setDale(int linie, int col, int x) {
        map[linie][col] = x;
    }

    public void inlocuire(int i1, int i2) {
        for(int linie = 0; linie < NrLinii; linie++) {
            for(int col = 0; col < NrCol; col++) {
                if(map[linie][col] == i1)
                    map[linie][col] = i2;
            }
        }
    }

    /**
     * 	folosit pentru a actualiza pozitia camerei
     * 	ydest e y unde trebuie sa ajunga
     * 	xdest asemenea
     * 	folosita in update
     */
    public void setCamera(int x, int y) {
        if(camera>0) {
            xdest = x;
            ydest = y;
        }
        else
        {
            this.x=x;
            this.y=y;
            camera++;
        }
    }


    public void update() {
        if(x < xdest) {
            x += viteza_tranzitie;
            if(x > xdest) {
                x = xdest;
            }
        }
        if(x > xdest) {
            x -= viteza_tranzitie;
            if(x < xdest) {
                x = xdest;
            }
        }
        if(y < ydest) {
            y += viteza_tranzitie;
            if(y > ydest) {
                y = ydest;
            }
        }
        if(y > ydest) {
            y -= viteza_tranzitie;
            if(y < ydest) {
                y = ydest;
            }
        }
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
        ColOffset = -this.x / daleSize;
        LiniiOffset = -this.y / daleSize;

        if(x != xdest || y != ydest)
            miscare = true;
        else
            miscare = false;
    }

    /**
     * functia de desenare a hartii
     */
    public void draw(Graphics2D g) {
        for(int linii = LiniiOffset; linii < LiniiOffset + ColDraw; linii++) {
            if(linii >= NrLinii)
                break;
            for(int col = ColOffset; col < ColOffset + LiniiDraw; col++) {
                if(col >= NrCol)
                    break;
                if(map[linii][col] == 0)
                    continue;
                int var = map[linii][col];
                int l = var / NrDale;
                int c = var % NrDale;
                g.drawImage(dales[l][c].getImage(), x + col * daleSize, y + linii * daleSize, null);
            }
        }
    }
}

