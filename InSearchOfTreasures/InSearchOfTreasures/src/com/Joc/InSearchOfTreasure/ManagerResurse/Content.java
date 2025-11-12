package com.Joc.InSearchOfTreasure.ManagerResurse;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.imageio.ImageIO;

public class Content {

    private final static String[] options = load_source(7);

    public static BufferedImage[][] MENUBG = incarca(options[0], 128, 143);
    public static BufferedImage[][] BAR = incarca(options[1], 128, 16);
    public static BufferedImage[][] POVESTE = incarca(options[2], 128, 144);
    public static BufferedImage[][] PLAYER = incarca(options[3], 16, 16);
    public static BufferedImage[][] Comoara = incarca(options[4], 16, 16);
    public static BufferedImage[][] ITEMS = incarca(options[5], 16, 16);
    public static BufferedImage[][] font = incarca(options[6], 8, 8);

    /**
     * functie de incarcare a dalelor in matrice de tip BufferedImage. Aceasta "Sparge" imaginea dupa dimensiuni si creaza matricea.
     * primește calea către o imagine și dimensiunile dorite ale fiecărui tile din imagine
     */
    public static BufferedImage[][] incarca(String s, int w, int h) {
        BufferedImage[][] ret;
        try {
            BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
            int width = spritesheet.getWidth() / w;
            int height = spritesheet.getHeight() / h;
            ret = new BufferedImage[height][width];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
                }
            }
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Nu s-au putut incarca corect dalele.");
            System.exit(0);
        }
        return null;
    }

    /**
     * functia scrie foloseste ASCII si imaginea cu litere pentru a le scrie pe ecran.
     * primește un context grafic (Graphics2D), un șir de caractere și coordonatele la care să afișeze textul
     */
    public static void scrie(Graphics2D g, String s, int x, int y) {
        s = s.toUpperCase();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == 47)
                ch = 36; /**< Pune / */
            if (ch == 58)
                ch = 37; /**< Pune : */
            if (ch == 32)
                ch = 38; /**< Space */
            if (ch >= 65 && ch <= 90)
                ch -= 65; /**< Pune litere */
            if (ch >= 48 && ch <= 57)
                ch -= 22; /**< Pune cifre */

                // utilizează imaginea font pentru a desena fiecare caracter pe ecran.
            int linii = ch / font[0].length;
            int col = ch % font[0].length;
            g.drawImage(font[linii][col], x + 8 * i, y, null);
        }
    }

    /**
     *  încarcă opțiunile (căile către fișierele de resurse) din baza de date
     */
    public static String[] load_source(int n) {
        Connection c = null;
        Statement stmt = null;
        int k = 0;
        String []options=new String[n];
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:optiuni.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM OPTIUNI;");
            while (rs.next()) {
                String romana = rs.getString("LIST");
                options[k] = romana;
                k++;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return options;
    }
}




