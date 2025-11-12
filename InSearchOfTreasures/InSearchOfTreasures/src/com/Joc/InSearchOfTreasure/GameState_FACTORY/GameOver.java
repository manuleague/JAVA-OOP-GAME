package com.Joc.InSearchOfTreasure.GameState_FACTORY;

import java.awt.Color;
import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.Joc.InSearchOfTreasure.GameState_FACTORY.GameLvl.GameLevels;
import com.Joc.InSearchOfTreasure.Main_SINGLETON.GamePanel;
import com.Joc.InSearchOfTreasure.ManagerResurse.Content;
import com.Joc.InSearchOfTreasure.ManagerResurse.Ceas;
import com.Joc.InSearchOfTreasure.ManagerResurse.GameStateManager;
import com.Joc.InSearchOfTreasure.ManagerResurse.Sound;
import com.Joc.InSearchOfTreasure.ManagerResurse.Control;

public class GameOver implements GameState {

    private Color color;
    private int rank;
    private long ticks; // timpul scurs de la inceperea jocului
    protected GameStateManager gsm;
    protected static int timp_pt_gameover=1800; // numarul maxim permis pt gameover
    protected static int scor=0;
    protected String []options=new String[3]; // textul pentru game over citit din datbase

    public GameOver(GameStateManager gsm) {
        this.gsm = gsm;
    }

    /**
     * Pt valoarea standard de game_over, acesta se obtine dupa 4 minute (3600 FPS=1 minut)
     * Rank-urile sunt pt 2 min=rank 1, pt 3 min rank 2, pt 4 rank 3, +4 min=>game over
     */
    public void init() {
        color = new Color(36, 175, 76);
        // calculează rangul jucătorului în funcție de timpul petrecut în joc și actualizează scorul
        ticks = Ceas.getCeas();
        if(ticks < (timp_pt_gameover*2))/*4 minute*/ {
            rank = 1;
            scor=scor+1000;
        }
        else if(ticks < (timp_pt_gameover*4))
        {
            rank = 2;
            scor=scor+700;
        }
        else if(ticks < (timp_pt_gameover*8))
        {
            rank = 3;
            scor=scor+500;
        }
        Connection c = null;
        Statement stmt = null;
        int k=0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:limba_menu.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LIMBA_OVER;");
            while (rs.next()) {
                String romana = rs.getString("Romana");
                String engleza = rs.getString("Engleza");
                if(GameMeniu.get_limba()==0) {
                    options[k]=romana;
                    k++;
                }
                else
                {
                    options[k]=engleza;
                    k++;
                }
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void update() {handleInput();}

    // deseneaza ecranul pentru gameover si afiseaza scorul, rankul...
    public void draw(Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT2);
        if(rank==1||rank==2||rank==3) {
            Content.scrie(g, options[0], 45, 36);
            int minutes = (int) (ticks / 3600);
            int seconds = (int) ((ticks / 60) % 60);
            Content.scrie(g, minutes + ":" + seconds, 44, 48);
            Content.scrie(g, "Rank", 48, 66);
            if (rank == 1) Content.scrie(g, "ESTI BUN", 50, 78);
            else if (rank == 2) Content.scrie(g, "MAI BUN", 24, 78);
            else if (rank == 3) Content.scrie(g, "NEMAIPOMENIT", 32, 78);
        }
        if(rank == 4) {
            Content.scrie(g, options[1], 30, 36);
            Content.scrie(g, "BOSS", 20, 78);
        }
        Content.scrie(g, options[2], 25, 110);
        if(GameLevels.getNivel_actual()==3)
        {
            Content.scrie(g,"Punctaj obtinut:",5, 10);
            Content.scrie(g,""+scor,50, 20);
        }
    }

    public void handleInput() {
        if(Control.isPress(Control.ESCAPE)) {
            if (rank==4) {
                gsm.setState(GameStateManager.MENIU);
                Sound.play("collect");
            }
            else {
                gsm.setState(GameStateManager.NXT);
                Sound.play("collect");
            }
        }
    }

    /**
     * Doar get si set static pt a seta timpul jocului care initial e 3600 FPS
     */
    public static void setTimp_pt_gameover(int x)
    {
        timp_pt_gameover=x;
    }
    public static int getTimp_gameover()
    {
        return (timp_pt_gameover*8);
    }

    @Override
    public void selectOption() {

    }
}
