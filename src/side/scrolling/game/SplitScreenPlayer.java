/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import java.awt.event.*;

public class SplitScreenPlayer {
    public String username;
    private int[] stats;
    public int x, y, direction = 1, deaths = 0, kills;
    public float veloX = 300F / 60F, speed;
    private float currentVeloX, currentVeloY;
    private boolean veloAddX = true, veloAddY = true, veloNegX = true, veloNegY = true;
    private boolean jumping, moving, alive = true, collision = false;
    private double decending, decendingSpeed = 0.5, df = 0, dj = 0;
    private int currentJump, jumps = 2, veloY = 15;
    private double maxHp = 100, hp;
    private double maxStanma = 80, stanma, stanmaPerSecond = 2;
    private int countDown = 3 * 60, control;
    private Font bigTitle = new Font("SanSerif", Font.BOLD, 100);
    private Font smallFont = new Font("SanSerif", Font.PLAIN, 10);
    
    public SplitScreenPlayer(int x, int y, int controlID, String username, int[] stats, Float speed){
        this.x = x;
        this.y = y;
        this.username = username;
        this.stats = stats;
        this.speed = speed;
        control = controlID;
        veloY += stats[0];
        jumps += stats[1];
        maxHp += (double)stats[2];
        maxStanma += (double)stats[3];
        stanmaPerSecond += (double)stats[4];
        veloX += speed / 60;
        currentJump = jumps;
        hp = maxHp;
        stanma = maxStanma;
    }
    
    public void update(boolean stable, int direction, SplitScreenMap m, Weapon w){
        if (hp < 1){
            alive = false;
        }
        if (stanma < maxStanma){
            stanma += stanmaPerSecond / 60;
        }
        respawnCheck(w);
        notMovingVeloX(m);
        if (m.sideCollision(control) != 0){
            collision = true;
        }
        if (moving){
            currentVeloX += veloX;
        }
        if (y > 400){
            alive = false;
        }
        for (int e = 0; e < m.potions.length; e++){
            if (m.potions[e].collisionDetection(getBounds()) && m.potions[e].getVisible()){
                hp += m.potions[e].getResources();
                break;
            }
        }
        for (int q = 0; q < m.z.length; q++){
            hp -= m.z[q].takeDamage(getBounds());
            if (m.z[q].killer == control){
                kills++;
            }
        }
        if (hp > maxHp){
            hp = maxHp;
        }
        if ((currentVeloX >= 1 || currentVeloX <= -1) && moving){
            while (true){
                collisionDetection(m);
                if (currentVeloX > 0){
                    x++;
                }
                else if (currentVeloX < 0){
                    x--;
                }
                if (currentVeloX >= 1){
                    currentVeloX--;
                }
                else if (currentVeloX <= -1){
                    currentVeloX++;
                }
                else{
                    break;
                }
            }
        }
        decending += decendingSpeed;
        if (!stable && !jumping){
            while (true){
                thudTest(m);
                //System.out.println(decending + " " + df);
                df++;
                if (df > decending){
                    df = 0;
                    break;
                }
                else{
                    y++;
                    //df++;
                    //decending--;
                }
            }
        }
        else{
            decending = 0;
        }
        if (jumping){
            currentVeloY = (float) -dj;
            if (currentVeloY == 0){
                jumping = false;
            }
            else{
                while (true){
                    if (m.topCollision(control)){
                        jumping = false;
                        break;
                    }
                    y--;
                    currentVeloY++;
                    if (currentVeloY == 0){
                        dj--;
                        if (dj == 0){
                            jumping = false;
                        }
                        break;
                    }
                }
            }
        }
    }
    
    public void thudTest(SplitScreenMap m){
        if (m.fallCollision(control)){
            decending = 0;
            currentJump = jumps;
        }
    }
    
    public void notMovingVeloX(SplitScreenMap m){
        if (m.sideCollision(control) == 0){
            veloAddX = true;
            veloNegX = true;
        }
    }
    
    public void collisionDetection(SplitScreenMap m){
        //System.out.println(direction + " " + x);
        int direct = m.sideCollision(control);
        if (currentVeloX < 0 && direct == 2){
            veloNegX = false;
            moving = false;
            currentVeloX = 0;
            //System.out.println("You hit the left side");
        }
        else if (currentVeloX > 0 && direct == 1){
            veloAddX = false;
            moving = false;
            currentVeloX = 0;
            //System.out.println("You hit the right side");
        }
        else{
            veloNegX = true;
            veloAddX = true;
        }
    }
    
    public void respawnCheck(Weapon w){
        if (!alive){
            countDown--;
            if (countDown == 0){
                respawn(w);
            }
        }
    }
    
    public void respawn(Weapon w){
        alive = true;
        x = 0 -380;
        y = -80 -200;
        jumps += stats[1];
        veloX += speed / 60;
        hp = maxHp;
        stanma = maxStanma;
        currentJump = jumps;
        veloAddX = true;
        veloAddY = true;
        veloNegX = true;
        veloNegY = true;
        countDown = 3 * 60;
        decending = 0;
        w.reset();
        deaths++;
    }
    
    public void drawPlayer(Graphics g){
        if (control == 1){
            g.drawRect(380 - 66, 200, 40, 80);
        }
        else if (control == 2){
            g.drawRect(380 + 683, 200, 40, 80);
        }
        if (!alive){
            g.setFont(bigTitle);
            g.setColor(Color.cyan);
            if (control == 1){
                g.drawString("" + (((int)countDown / 60) + 1), 380 - 66, 200);
            }
            else if (control == 2){
                g.drawString("" + (((int)countDown / 60) + 1), 380 + 683, 200);
            }
        }
    }
    
    public void drawHealthAndStanama(Graphics g){
        if (control == 1){
            g.setFont(smallFont);
            g.setColor(Color.black);
            g.fillRect(10, 10, 290, 70);
            g.setColor(Color.red);
            g.fillRect(90, 20, 200, 20);
            g.setColor(Color.GREEN);
            g.fillRect(90, 20, (int) ((hp / maxHp) * 200), 20);
            g.setColor(Color.WHITE);
            g.fillOval(10, 10, 70, 70);
            g.drawString((int)hp + "/" + (int)maxHp, 95, 35);
            g.setColor(Color.BLUE);
            g.drawRect(90, 50, 200, 20);
            g.fillRect(90, 50, (int) ((stanma / maxStanma) * 200), 20);
            g.setColor(Color.WHITE);
            g.drawString((int)stanma + "/" + (int)maxStanma, 95, 65);
        }
        else{
            g.setFont(smallFont);
            g.setColor(Color.black);
            g.fillRect(10 + 683, 10, 290, 70);
            g.setColor(Color.red);
            g.fillRect(90 + 683, 20, 200, 20);
            g.setColor(Color.GREEN);
            g.fillRect(90 + 683, 20, (int) ((hp / maxHp) * 200), 20);
            g.setColor(Color.WHITE);
            g.fillOval(10 + 683, 10, 70, 70);
            g.drawString((int)hp + "/" + (int)maxHp, 95 + 683, 35);
            g.setColor(Color.BLUE);
            g.drawRect(90 + 683, 50, 200, 20);
            g.fillRect(90 + 683, 50, (int) ((stanma / maxStanma) * 200), 20);
            g.setColor(Color.WHITE);
            g.drawString((int)stanma + "/" + (int)maxStanma, 95 + 683, 65);
        }
    }
    
    public void drawStats(Graphics g, double ammo, double ammoMax, Float reloadingDivision, String gun){
        if (control == 1){
            g.setFont(smallFont);
            g.setColor(Color.BLACK);
            g.fillRect(680 - 66 - 66, 300, 100, 160);
            g.setColor(Color.BLUE);
            g.drawString(username, 685 - 66 - 66, 315);
            g.drawLine(680 - 66 - 66, 320, 775 - 66 - 66, 320);
            g.drawString("K: " + kills + " D: " + deaths + " A: " + 0, 685 - 66 - 66, 335);
            g.drawLine(680 - 66 - 66, 340, 775 - 66 - 66, 340);
            g.drawString("X: " + (x + 380) + " Y: " + (y + 200), 685 - 66 - 66, 355);
            g.drawLine(680 - 66 - 66, 360, 775 - 66 - 66, 360);
            g.drawString(gun, 685 - 66 - 66, 375);
            g.drawLine(680 - 66 - 66, 380, 775 - 66 - 66, 380);
            g.drawString("ammo: " + (int)ammo + "/" + (int)ammoMax, 685 - 66 - 66, 395);
            g.drawRect(685 - 66 - 66, 405, 80, 10);
            if (reloadingDivision <= 0F){
                g.fillRect(685 - 66 - 66, 405, (int) ((ammo / ammoMax) * 80), 10);
            }
            else{
                double d = reloadingDivision;
                g.fillRect(685 - 66 - 66, 405, (int)d, 10);
            }
            g.drawLine(680 - 66 - 66, 425, 775 - 66 - 66, 425);
        }
        else{
            g.setFont(smallFont);
            g.setColor(Color.BLACK);
            g.fillRect(680 + 683 - 66 - 66, 300, 100, 160);
            g.setColor(Color.BLUE);
            g.drawString(username, 685 + 683 - 66 - 66, 315);
            g.drawLine(680 + 683 - 66 - 66, 320, 775 + 683 - 66 - 66, 320);
            g.drawString("K: " + kills + " D: " + deaths + " A: " + 0, 685 + 683 - 66 - 66, 335);
            g.drawLine(680 + 683 - 66 - 66, 340, 775 + 683 - 66 - 66, 340);
            g.drawString("X: " + (x + 380) + " Y: " + (y + 200), 685 + 683 - 66 - 66, 355);
            g.drawLine(680 + 683 - 66 - 66, 360, 775 + 683 - 66 - 66, 360);
            g.drawString(gun, 685 + 683 - 66 - 66, 375);
            g.drawLine(680 + 683 - 66 - 66, 380, 775 + 683 - 66 - 66, 380);
            g.drawString("ammo: " + (int)ammo + "/" + (int)ammoMax, 685 + 683 - 66 - 66, 395);
            g.drawRect(685 + 683 - 66 - 66, 405, 80, 10);
            if (reloadingDivision <= 0F){
                g.fillRect(685 + 683 - 66 - 66, 405, (int) ((ammo / ammoMax) * 80), 10);
            }
            else{
                double d = reloadingDivision;
                g.fillRect(685 + 683 - 66 - 66, 405, (int)d, 10);
            }
            g.drawLine(680 + 683 - 66 - 66, 425, 775 + 683 - 66 - 66, 425);
        }
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (alive){
            if ((key == KeyEvent.VK_RIGHT && control == 1) || (key == KeyEvent.VK_D && control == 2)){
                if (veloAddX){
                    this.moving = true;
                }
                if (veloX < 0){
                    veloX *= -1;
                }
                direction = 1;
            }
            if ((key == KeyEvent.VK_LEFT && control == 1) || (key == KeyEvent.VK_A && control == 2)){
                if (collision){
                    moving = true;
                }
                if (veloNegX){
                    moving = true;
                }
                if (veloX > 0){
                    veloX *= -1;
                }
                direction = 2;
            }
            if ((key == KeyEvent.VK_UP && control == 1) || (key == KeyEvent.VK_W && control == 2)){
                if (currentJump > 0){
                    currentJump--;
                    jumping = true;
                    currentVeloY = -veloY;
                    dj = veloY;
                }
            }
        }
//        if (key == KeyEvent.VK_DOWN){
//            
//        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        //To get rid of annoying render hen changing moving directions
        if ((key == KeyEvent.VK_RIGHT && control == 1) || (key == KeyEvent.VK_D && control == 2) && veloX > 0){
            currentVeloX = 0;
            moving = false;
        }
        if ((key == KeyEvent.VK_LEFT && control == 1) || (key == KeyEvent.VK_A && control == 2) && veloX < 0){
            currentVeloX = 0;
            moving = false;
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Rectangle getBounds(){
        return new Rectangle(x + 380, y + 200, 40, 80);
    }
}