/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Arthur
 */
public class Map extends JPanel{
    Color background = new Color(50, 130, 50);
    Player p;
    GrassDirt[] gb;
    Rectangle[] gbRect;
    Potion potions[];
    int[] zspawn;
    ArrayList <Zombie> z = new ArrayList();
    Chat c;
    Weapon w;
    public boolean showData, paused;
    int rounds = 1, mode, num;
    Font biggerFont = new Font("SanSerif", Font.BOLD, 20);
    ImageIcon img = new ImageIcon(new ImageIcon("Images/Pause.png").getImage());
    ImageIcon img2 = new ImageIcon(new ImageIcon("Images/Resume.png").getImage());
    ImageIcon img3 = new ImageIcon(new ImageIcon("Images/Exit.png").getImage());
    
    public Map(Player p, Chat c, Weapon w, int map, int mode, int num){
        if (map == 1){
            GrassDirt[] gb = {new GrassDirt(-400, -300), new GrassDirt(-350, -300), new GrassDirt(-100, -300),
            new GrassDirt(-50, -300), new GrassDirt(0, -300), new GrassDirt(50, -300), new GrassDirt(300, -300),
            new GrassDirt(350, -300), new GrassDirt(-200, -150), new GrassDirt(150, -150), new GrassDirt(-150, -100),
            new GrassDirt(100, -100), new GrassDirt(-450, 0), new GrassDirt(-400, 0), new GrassDirt(-350, 0), 
            new GrassDirt(-300, 0), new GrassDirt(-250, 0), new GrassDirt(-150, 0), new GrassDirt(-50, 0),
            new GrassDirt(0, 0), new GrassDirt(100, 0), new GrassDirt(200, 0), new GrassDirt(250, 0), 
            new GrassDirt(300, 0), new GrassDirt(350, 0), new GrassDirt(400, 0), new GrassDirt(-100, 50),
            new GrassDirt(50, 50), new GrassDirt(-250, 150), new GrassDirt(200, 150), new GrassDirt(-200, 200),
            new GrassDirt(-150, 200), new GrassDirt(-100, 200), new GrassDirt(-50, 200), new GrassDirt(0, 200),
            new GrassDirt(50, 200), new GrassDirt(100, 200), new GrassDirt(150, 200)};
            this.gb = gb;
            Potion[] po = {new Potion(1, 1, -50, -350), new Potion(1, 1, -450, -50), new Potion(1, 1, 400, -50),
            new Potion(1, 1, 0, 150)};
            this.potions = po;
            int[] spawning = {-350, -400, -100, -400, 0, -400, 50, -400, 300, -400, -400, -100, -350, -100, -300, -100,
            -250, -100, 200, -100, 250, -100, 300, -100, 350, -100, -150, 100, -100, 100, -50, 100, 50, 100, 100, 100}; 
            zspawn = spawning;
        }
        else if (map == 2){
            GrassDirt[] gb = {new GrassDirt(-950, -500), new GrassDirt(850, -500), new GrassDirt(-950, -450),
            new GrassDirt(-500, -450), new GrassDirt(-450, -450), new GrassDirt(-400, -450), new GrassDirt(250, -450),
            new GrassDirt(300, -450), new GrassDirt(350, -450), new GrassDirt(850, -450), new GrassDirt(-950, -400),
            new GrassDirt(-250, -400), new GrassDirt(-200, -400), new GrassDirt(-150, -400), new GrassDirt(850, -400), 
            new GrassDirt(-950, -350), new GrassDirt(-750, -350), new GrassDirt(-700, -350), new GrassDirt(-650, -350),
            new GrassDirt(0, -350), new GrassDirt(50, -350), new GrassDirt(100, -350), new GrassDirt(500, -350), 
            new GrassDirt(550, -350), new GrassDirt(600, -350), new GrassDirt(850, -350), new GrassDirt(-950, -300),
            new GrassDirt(650, -300), new GrassDirt(850, -300), new GrassDirt(800, -250), new GrassDirt(-900, -250),
            new GrassDirt(-850, -200), new GrassDirt(750, -200), new GrassDirt(-800, -150), new GrassDirt(700, -150),
            new GrassDirt(-750, -100), new GrassDirt(650, -100), new GrassDirt(-700, -50),//
            new GrassDirt(-500, -50), new GrassDirt(-450, -50), new GrassDirt(350, -50), new GrassDirt(700, -50),
            new GrassDirt(-700, 0), new GrassDirt(-650, 0), new GrassDirt(-600, 0), new GrassDirt(-550, 0),
            new GrassDirt(-400, 0), new GrassDirt(-350, 0), new GrassDirt(-300, 0), new GrassDirt(-200, 0),
            new GrassDirt(-150, 0), new GrassDirt(-100, 0), new GrassDirt(-50, 0), new GrassDirt(0, 0),
            new GrassDirt(50, 0), new GrassDirt(250, 0), new GrassDirt(300, 0), new GrassDirt(400, 0),
            new GrassDirt(450, 0), new GrassDirt(500, 0), new GrassDirt(550, 0), new GrassDirt(700, 0),
            new GrassDirt(-250, 50), new GrassDirt(100, 50), new GrassDirt(150, 50), new GrassDirt(200, 50),
            new GrassDirt(600, 50), new GrassDirt(650, 50)};
            this.gb = gb;
            Potion[] po = {new Potion(1, 1, 150, 0), new Potion(1, 1, -400, -50)};
            this.potions = po;
            int[] spawning = {-450, -550, 300, -550, 200, -500, -700, -550, 50, -550, 550, -550, -800, -250, 750, -300,
            700, -250, -700, -150, 500, -150, 450, -150, -200, -100, -150, -100, 50, -100, 250, -100, 300, -100, 550, -100}; 
            zspawn = spawning;
        }
        else if (map == 3){
            GrassDirt[] gb = {new GrassDirt(-300, -750), new GrassDirt(-250, -750), new GrassDirt(-200, -750),
            new GrassDirt(-150, -750), new GrassDirt(-100, -750), new GrassDirt(-50, -750), new GrassDirt(0, -750),
            new GrassDirt(50, -750), new GrassDirt(100, -750), new GrassDirt(150, -750), new GrassDirt(200, -750),
            new GrassDirt(250, -750), new GrassDirt(300, -750), new GrassDirt(-400, -700), new GrassDirt(350, -700),
            new GrassDirt(-450, -650), new GrassDirt(400, -650), new GrassDirt(-500, -600), new GrassDirt(450, -600),
            new GrassDirt(-500, -550), new GrassDirt(450, -550), new GrassDirt(-550, -500), new GrassDirt(500, -500),
            new GrassDirt(-550, -450), new GrassDirt(500, -450), new GrassDirt(-600, -400), new GrassDirt(550, -400),
            new GrassDirt(-600, -350), new GrassDirt(550, -350), new GrassDirt(-600, -300), new GrassDirt(-400, -300),
            new GrassDirt(-350, -300), new GrassDirt(-300, -300), new GrassDirt(250, -300), new GrassDirt(300, -300),
            new GrassDirt(550, -300), new GrassDirt(-650, -250), new GrassDirt(-250, -250), new GrassDirt(200, -250),
            new GrassDirt(600, -250), new GrassDirt(-650, -200), new GrassDirt(600, -200), new GrassDirt(-650, -150),
            new GrassDirt(600, -150), new GrassDirt(-650, 0), new GrassDirt(-150, 0), new GrassDirt(100, 0),
            new GrassDirt(600, 0), new GrassDirt(-600, 50), new GrassDirt(-100, 50), new GrassDirt(-50, 50),
            new GrassDirt(0, 50), new GrassDirt(50, 50), new GrassDirt(550, 50), new GrassDirt(-600, 100),
            new GrassDirt(550, 100), new GrassDirt(-600, 150), new GrassDirt(550, 150), new GrassDirt(-550, 200),
            new GrassDirt(500, 200), new GrassDirt(-550, 250), new GrassDirt(500, 250), new GrassDirt(-500, 300),
            new GrassDirt(450, 300), new GrassDirt(-500, 350), new GrassDirt(450, 350), new GrassDirt(-450, 400),
            new GrassDirt(400, 400), new GrassDirt(-400, 450), new GrassDirt(350, 450), new GrassDirt(-350, 500),
            new GrassDirt(-300, 500), new GrassDirt(-250, 500), new GrassDirt(-200, 500), new GrassDirt(-150, 500),
            new GrassDirt(-100, 500), new GrassDirt(-50, 500), new GrassDirt(0, 500), new GrassDirt(50, 500),
            new GrassDirt(100, 500), new GrassDirt(150, 500), new GrassDirt(200, 500), new GrassDirt(250, 500),
            new GrassDirt(300, 500)
            };
            this.gb = gb;
            Potion[] po = {new Potion(1, 1, -250, -300), new Potion(1, 1, 200, -300)};
            this.potions = po;
            int[] spawning = {-400, -400, -350, -400, -300, -400, -250, -400, 200, -400, 250, -400, 300, -400, 350, -400, -150, -100,
            100, -100, -600, -50, -550, 100, -500, 200, -450, 300, -400, 350, -350, 400, -150, 400, -50, 400, 0, 400,
            100, 400, 200, 400, 300, 400, 350, 350, 400, 300, 450, 200, 500, 100, 600, -50}; 
            zspawn = spawning;
        }
        this.num = num;
        this.mode = mode;
        gbRect = new Rectangle[gb.length];
        this.p = p;
        this.c = c;
        this.w = w;
        for (int r = 0; r < gb.length; r++){
            gbRect[r] = gb[r].getBounds();
        }
        z.add(new Zombie(zspawn));
    }
    
    public void update(int num, boolean paused){
        this.num = num;
        int nums = 0;
        this.paused = paused;
        for (int g = 0; g < potions.length; g++){
            potions[g].update(p);
        }
        for (int g = 0; g < z.size(); g++){
            if (!z.get(g).alive){
                nums++;
                if (nums >= z.size()){
                    rounds++;
                    z.add(new Zombie(zspawn));
                    for (int q = 0; q < z.size(); q++){
                        z.get(q).respawn();
                    }
                }
            }
        }
        Rectangle[] rect = new Rectangle[z.size()];
        for (int q = 0; q < rect.length; q++){
            rect[q] = new Rectangle(z.get(q).x + 35, z.get(q).y, 10, 80);
        }
        for (int g = 0; g < z.size(); g++){
            z.get(g).update(this, w.damageTaker(new Rectangle(z.get(g).x, z.get(g).y, 40, 80), z.get(g).alive), w.control, z, g);
        }
    }
    
    public void getPaused(boolean paused){
        this.paused = paused;
    }
    
    public boolean collision(){
        for (int e = 0; e < gb.length; e++){
            if (gb[e].collisionDetection(p.getBounds())){
                return true;
            }
        }
        return false;
    }
    
    public boolean Itemcollision(){
        for (int e = 0; e < potions.length; e++){
            if (potions[e].collisionDetection(p.getBounds())){
                return true;
            }
        }
        return false;
    }
    
    public boolean fallCollision(){
        for (int e = 0; e < gb.length; e++){
            if (gb[e].collisionThumpDetection(p.getBounds())){
                return true;
            }
        }
        return false;
    }
    
    public boolean topCollision(){
        for (int e = 0; e < gb.length; e++){
            if (gb[e].collisionTopDetection(p.getBounds())){
                return true;
            }
        }
        return false;
    }
    
    public int sideCollision(){
        for (int e = 0; e < gb.length; e++){
            if (gb[e].collisionSideDetection(p.getBounds()) != 0){
                return gb[e].collisionSideDetection(p.getBounds());
            }
        }
        return 0;
    }
    
    public void drawWords(Graphics g){
        int min = 0;
        int sec = 0;
        int num2 = num;
        while(num2 >= 60){
            num2 -= 60;
            min++;
        }
        sec = num2;
        g.setFont(biggerFont);
        if (sec >= 10){
            g.drawString(min + ":" + sec, 700, 290);
        }
        else{
            g.drawString(min + ":0" + sec, 700, 290);
        }
    }
    
    public void paint(Graphics g){
        try{
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(background);
            g2.fillRect(0, 0, 800, 500);
            //
            g2.setColor(Color.blue);
    //        g.drawRect(-100 - p.getX() + 50, -50 - p.getY(), p.veloX, 50);
    //        g.drawRect(-100 - p.getX() - p.veloX, -50 - p.getY(), p.veloX, 50);
            //
            g2.setColor(Color.RED);
            g2.setColor(Color.RED);
            for (int e = 0; e < gb.length; e++){
                gb[e].drawBlock(this, g2, p.getX(), p.getY());
            }
            for (int e = 0; e < potions.length; e++){
                potions[e].drawPotion(this, g2, p.getX(), p.getY());
            }
            for (int e = 0; e < z.size(); e++){
                z.get(e).drawZombie(this, g2, p.x, p.y);
            }
            w.drawBullets(this, g2, p.x, p.y);
            p.drawHealthAndStanama(this, g2);
            p.drawStats(g2, (double)w.bullets, (double)w.bulletCount, w.rd, w.name, rounds);
            c.render(g2);
            img3.paintIcon(this, g2, 700, 20);
            p.drawPlayer(this, g2);
            if (paused){
                img.paintIcon(this, g2, 740, 20);
            }
            else{
                img2.paintIcon(this, g2, 740, 20);
            }
            if (mode == 1){
                drawWords(g2);
            }
        }catch(Exception e){
            g.drawString("Loading...", 0, 0);
        }
    }
}