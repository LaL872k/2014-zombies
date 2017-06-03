/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import java.util.*;
import javax.swing.ImageIcon;

/**
 *
 * @author Arthur
 */
public class Potion {
    private boolean visible = true, wasVisible = true;
    public int healthGain, id, level, x, y;
    private float timeTillRespawn;
    ImageIcon img = new ImageIcon(new ImageIcon("Images/HealthPotion.gif").getImage());
    
    public Potion(int potionID, int level, int x, int y){
        id = potionID;
        this.level = level;
        this.x = x;
        this.y = y; 
        
        if (level == 1){
            healthGain = 50;
        }
        else if (level == 2){
            healthGain = 100;
        }
        else if (level == 3){
            healthGain = 120;
        }
    }
    
    public void update(Player p){
        if (!visible){
            wasVisible = false;
        }
        if (collisionDetection(p.getBounds()) && visible){
            potionColected();
        }
        if (!visible){
            timeTillRespawn -= (double) 1 / 60;
        }
        if (timeTillRespawn <= 0){
            visible = true;
            wasVisible = true;
        }
    }
    
    public void update2(SplitScreenPlayer p){
        if (!visible){
            wasVisible = false;
        }
        if (collisionDetection(p.getBounds()) && visible){
            potionColected();
        }
        if (!visible){
            timeTillRespawn -= (double) 1 / 60;
        }
        if (timeTillRespawn <= 0){
            visible = true;
            wasVisible = true;
        }
    }
    
    public void potionColected(){
        visible = false;
        Random randomGenerator = new Random();
        timeTillRespawn = randomGenerator.nextInt((10) + 15);
    }
    
    public void drawPotion(Component h, Graphics g, int x, int y){
        if (visible){
            img.paintIcon(h, g, this.x - x, this.y - y);
        }
    }
    
    public boolean collisionDetection(Rectangle rect){
        return getBounds().intersects(rect);
    }
    
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getResources(){
        return healthGain;
    }
    public boolean getVisible(){
        return wasVisible;
    }
    public Rectangle getBounds(){
        return new Rectangle(x, y, 50, 50);
    }
}