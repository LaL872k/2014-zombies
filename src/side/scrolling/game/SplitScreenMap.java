/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Arthur
 */
public class SplitScreenMap extends JPanel{
    SplitScreenPlayer p1;
    SplitScreenPlayer p2;
    GrassDirt[] gb = {new GrassDirt(-400, -400), new GrassDirt(-350, -400), new GrassDirt(-300, -400), 
    new GrassDirt(-200, -400), new GrassDirt(-150, -400), new GrassDirt(-100, -400), new GrassDirt(50, -400),
    new GrassDirt(350, -400), new GrassDirt(100, -350), new GrassDirt(350, -350), new GrassDirt(150, -300),
    new GrassDirt(200, -300), new GrassDirt(250, -300), new GrassDirt(350, -300), new GrassDirt(-750, -200),
    new GrassDirt(-50, -200), new GrassDirt(-100, -150), new GrassDirt(-150, -100), new GrassDirt(200, -100), 
    new GrassDirt(-450, -50), new GrassDirt(-400, -50), new GrassDirt(-350, -50), new GrassDirt(-300, -50), 
    new GrassDirt(-250, -50), new GrassDirt(-200, -50), new GrassDirt(50, -50), new GrassDirt(100, -50),
    new GrassDirt(150, -50), new GrassDirt(200, -50), new GrassDirt(250, -50), new GrassDirt(300, -50),
    new GrassDirt(350, -50), new GrassDirt(400, -50), new GrassDirt(100, 0), new GrassDirt(350, 0),
    new GrassDirt(350, 50), new GrassDirt(-400, 100), new GrassDirt(-50, 100), new GrassDirt(200, 100),
    new GrassDirt(350, 100), new GrassDirt(-400, 150), new GrassDirt(-350, 150), new GrassDirt(-300, 150), 
    new GrassDirt(-250, 150), new GrassDirt(-150, 150), new GrassDirt(-100, 150), new GrassDirt(-50, 150),
    new GrassDirt(0, 150), new GrassDirt(50, 150), new GrassDirt(100, 150), new GrassDirt(150, 150), 
    new GrassDirt(200, 150), new GrassDirt(250, 150), new GrassDirt(300, 150), new GrassDirt(350, 150)};
    Rectangle[] gbRect = new Rectangle[gb.length];
    SplitScreenZombie z[] = {};
    Image img;
    Potion potions[] = {};
    Chat c;
    Weapon w;
    Weapon w2;
    public boolean showData;
    
    public SplitScreenMap(SplitScreenPlayer p, SplitScreenPlayer p2, Chat c, Weapon w, Weapon w2){
        this.p1 = p;
        this.p2 = p2;
        this.c = c;
        this.w = w;
        this.w2 = w2;
        for (int r = 0; r < gb.length; r++){
            gbRect[r] = gb[r].getBounds();
        }
        //img = new Image("GrassDirtBlock.png");
    }
    
    public void update(){
        for (int g = 0; g < potions.length; g++){
            potions[g].update2(p1);
        }
        for (int g = 0; g < z.length; g++){
            z[g].update(this, w.damageTaker(new Rectangle(z[g].x, z[g].y, 40, 80), z[g].alive), w.control);
            z[g].takeDamageUpdate(w2.damageTaker(new Rectangle(z[g].x, z[g].y, 40, 80), z[g].alive), w2.control);
        }
    }
    
    public boolean collision(int num){
        if (num == 1){
            for (int e = 0; e < gb.length; e++){
                if (gb[e].collisionDetection(p1.getBounds())){
                    return true;
                }
            }
        }
        else{
            for (int e = 0; e < gb.length; e++){
                if (gb[e].collisionDetection(p2.getBounds())){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean Itemcollision(int num){
        if (num == 1){
            for (int e = 0; e < potions.length; e++){
                if (potions[e].collisionDetection(p1.getBounds())){
                    return true;
                }
            }
        }
        else{
            for (int e = 0; e < potions.length; e++){
                if (potions[e].collisionDetection(p2.getBounds())){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean fallCollision(int num){
        if (num == 1){
            for (int e = 0; e < gb.length; e++){
                if (gb[e].collisionThumpDetection(p1.getBounds())){
                    return true;
                }
            }
        }
        else{
            for (int e = 0; e < gb.length; e++){
                if (gb[e].collisionThumpDetection(p2.getBounds())){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean topCollision(int num){
        if (num == 1){
            for (int e = 0; e < gb.length; e++){
                if (gb[e].collisionTopDetection(p1.getBounds())){
                    return true;
                }
            }
        }
        else{
            for (int e = 0; e < gb.length; e++){
                if (gb[e].collisionTopDetection(p2.getBounds())){
                    return true;
                }
            }
        }
        return false;
    }
    
    public int sideCollision(int num){
        if (num == 1){
            for (int e = 0; e < gb.length; e++){
                if (gb[e].collisionSideDetection(p1.getBounds()) != 0){
                    return gb[e].collisionSideDetection(p1.getBounds());
                }
            }
        }
        else{
            for (int e = 0; e < gb.length; e++){
                if (gb[e].collisionSideDetection(p2.getBounds()) != 0){
                    return gb[e].collisionSideDetection(p2.getBounds());
                }
            }
        }
        return 0;
    }
    
    public void paint(Graphics g){
        try{
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(img, 10, 10, this);
            g2.setColor(Color.green);
            g2.fillRect(0, 0, 1366, 500);
            g2.setColor(Color.BLACK);
            g2.drawLine(683, 0, 683, 500);
            //
            g2.setColor(Color.RED);
            p1.drawPlayer(g2);
            if (p2.x - p1.x < 341){
                g2.drawRect(p2.x - p1.x - 66 + 380, p2.y - p1.y + 200, 40, 80);
            }
            g2.setColor(Color.RED);
            for (int e = 0; e < gb.length; e++){
                if (gb[e].x - p1.x < 683 && gb[e].x - p1.x > 0 - gb[e].width && gb[e].y - p1.y > 0 - gb[e].height && gb[e].y - p1.y < 500){
                    gb[e].drawBlock(this, g2, p1.getX() + 66, p1.getY());
                }
            }
            for (int e = 0; e < potions.length; e++){
                if (potions[e].x - p1.x < 683 && potions[e].x - p1.x > 0 - 50 && potions[e].y - p1.y > 0 - 50 && potions[e].y - p1.y < 500){
                    potions[e].drawPotion(this, g2, p1.getX() + 66, p1.getY());
                }
            }
            for (int e = 0; e < z.length; e++){
                if (z[e].x - p1.x < 683 && z[e].x - p1.x > 0 - 40 && z[e].y - p1.y > 0 - 80 && z[e].y - p1.y < 500){
                    z[e].drawZombie(g2, p1.x + 66, p1.y);
                }
            }
            w.drawBulletSide(g2, p1.x + 112 - 50, p1.y, 1);
            w2.drawBulletSide(g2, p1.x + 66, p1.y, 1);
            p1.drawHealthAndStanama(g2);
            p1.drawStats(g2, (double)w.bullets, (double)w.bulletCount, w.rd, w.name);
            c.render(g2);
            //
            //
            //
            g2.setColor(Color.RED);
            p2.drawPlayer(g2);
            if (p1.x - p2.x > -400){
                g2.drawRect((p1.x - p2.x) + 683 + 380, p1.y - p2.y + 200, 40, 80);
            }
            g2.setColor(Color.RED);
            for (int e = 0; e < gb.length; e++){
                if (gb[e].x - p2.x < 683 && gb[e].x - p2.x > 0 - gb[e].width && gb[e].y - p2.y > 0 - gb[e].height && gb[e].y - p2.y < 500){
                    gb[e].drawBlockSide(g2, p2.getX(), p2.getY());
                }
            }
            for (int e = 0; e < potions.length; e++){
                if (potions[e].x - p2.x < 683 && potions[e].x - p2.x > 0 - 50 && potions[e].y - p2.y > 0 - 50 && potions[e].y - p2.y < 500){
                    potions[e].drawPotion(this, g2, p2.getX() + 683 + 380, p2.getY() + 200);
                }
            }
            for (int e = 0; e < z.length; e++){
                //if (z[e].x - p2.x < 683 && z[e].x - p2.x > 0 - 40 && z[e].y - p2.y > 0 - 80 && z[e].y - p2.y < 500){
                    z[e].drawZombie(g2, p2.x - 683, p2.y);
                //}
            }
            w2.drawBulletSide(g2, p2.x - 683, p2.y, 2);
            w.drawBulletSide(g2, p2.x - 683, p2.y, 2);
            p2.drawHealthAndStanama(g2);
            p2.drawStats(g2, (double)w2.bullets, (double)w2.bulletCount, w2.rd, w2.name);
            c.render(g2);
        }catch(Exception e){
            g.drawString("Loading...", 0, 0);
        }
    }
}