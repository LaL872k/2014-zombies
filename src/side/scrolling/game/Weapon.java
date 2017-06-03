/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.ImageIcon;


public class Weapon {
    ImageIcon img = new ImageIcon(new ImageIcon("Images/Bullet.png").getImage());
    String name;
    int damage, bullets, bulletCount, attackID, distance, dt = 0, control;
    Float reloadTime, reloadTester = 0F, rateOfFire, rateTester = 0F, bulletSpeed, dtester = 0F, rd = 0F;
    boolean reloading, firable = true, keyHolded;
    ArrayList <Bullet>ammo = new ArrayList();
    
    public Weapon(String name, int damage, int bulletCount, int bulletDistance, Float bulletSpeed, Float reloadTime, Float rateOfFire, int controlID){
        this.damage = damage;
        this.bulletCount = bulletCount;
        this.rateOfFire = rateOfFire;
        this.reloadTime = reloadTime;
        this.distance = bulletDistance;
        this.bulletSpeed = bulletSpeed;
        this.name = name;
        bullets = bulletCount;
        control = controlID;
    }
    
    public void update(int x, int y, int direction){
        if (bullets <= 0 || reloading){
            reloadTester += 1/60F;
            rd = (reloadTester / reloadTime) * 80F;
            if (reloadTester > reloadTime){
                firable = true;
                reloadTester = 0F;
                bullets = bulletCount;
                reloading = false;
                rd = 0F;
            }
        }
        else if (!firable){
            //System.out.println("ReloadTime: " + reloadTime + " ReloadTester: " + reloadTester + " RateOfFire: " + rateOfFire + " RateTester: " + rateTester + " BulletSpeed: " + bulletSpeed);
            rateTester += 1/60F;
            if (rateTester > rateOfFire){
                firable = true;
                rateTester = 0F;
            }
        }
        if (keyHolded && firable){
            fire(x, y, direction);
        }
        for (int q = 0; q < ammo.size(); q++){
            if (ammo.get(q).d == 1){
                ammo.get(q).dtest += bulletSpeed;
                for (int k = 0; k < ammo.get(q).dtest; k++){
                    if (ammo.get(q).dtest >= 1){
                        ammo.get(q).dtest--;
                        ammo.get(q).x--;
                        ammo.get(q).dt++;
                    }
                }
                if (ammo.get(q).dt > ammo.get(q).dist){
                    ammo.remove(q);
                }
            }
            else if (ammo.get(q).d == 2){
                ammo.get(q).dtest += bulletSpeed;
                for (int k = 0; k < ammo.get(q).dtest; k++){
                    if (ammo.get(q).dtest >= 1){
                        ammo.get(q).dtest--;
                        ammo.get(q).x++;
                        ammo.get(q).dt++;
                    }
                }
                if (ammo.get(q).dt > ammo.get(q).dist){
                    ammo.remove(q);
                }
            }
        }
    }
    
    public void fire(int x, int y, int direction){
        firable = false;
        bullets--;
        if (direction == 2){
            ammo.add(new Bullet(x, y + 20, 10, 5, 1, distance, 0, 0F));
        }
        else if (direction == 1){
            ammo.add(new Bullet(x + 40 - 10, y + 20, 10, 5, 2, distance, 0, 0F));
        }
    }
    
    public void reset(){
        bullets = bulletCount;
    }
    
    public void collsion(Rectangle[] rect){
        for (int u = 0; u < rect.length; u++){
            for (int i = 0; i < ammo.size(); i++){
                if (new Rectangle(ammo.get(i).x + 380, ammo.get(i).y + 200, ammo.get(i).width, ammo.get(i).height).intersects(rect[u])){
                    ammo.remove(i);
                }
            }
        }
    }
    
    public void drawBullets(Component h, Graphics g, int x, int y){
        g.setColor(Color.YELLOW);
        for (int q = 0; q < ammo.size(); q++){
            img.paintIcon(h, g, ammo.get(q).x - x + 380, ammo.get(q).y - y + 200);
        }
    }
    
    public void drawBulletSide(Graphics g, int x, int y, int control){
        g.setColor(Color.YELLOW);
        for (int q = 0; q < ammo.size(); q++){
            if ((ammo.get(q).x - x + 380 <= 683 && control == 1) || (ammo.get(q).x - x - 380 + 80 >= 0 && control == 2)){
                g.fillRect(ammo.get(q).x - x + 380, ammo.get(q).y - y + 200, ammo.get(q).width, ammo.get(q).height);
            }
        }
    }
    
    public int damageTaker(Rectangle rect, boolean alive){
        for (int r = 0; r < ammo.size(); r++){
            if (rect.intersects(ammo.get(r).x + 380, ammo.get(r).y + 200, ammo.get(r).width, ammo.get(r).height) && alive){
                ammo.remove(r);
                return damage;
            }
        }
        return 0;
    }
    
    public void reset(String name, int damage, int bulletCount, int bulletDistance, Float bulletSpeed, Float reloadTime, Float rateOfFire, int controlID){
        this.damage = damage;
        this.bulletCount = bulletCount;
        this.rateOfFire = rateOfFire;
        this.reloadTime = reloadTime;
        this.distance = bulletDistance;
        this.bulletSpeed = bulletSpeed;
        this.name = name;
        reloading = false;
        bullets = bulletCount;
        control = controlID;
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_SPACE && control == 1) || (key == KeyEvent.VK_1 && control == 2)){
            keyHolded = true;
        }
        if (key == KeyEvent.VK_R){
            reloading = true;
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_SPACE && control == 1) || (key == KeyEvent.VK_1 && control == 2)){
            keyHolded = false;
        }
    }
}