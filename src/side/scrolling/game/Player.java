/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.ImageIcon;

public class Player {
    ImageIcon pro;
    ImageIcon r = new ImageIcon(new ImageIcon("Images/HumanStandLeft.png").getImage());
    ImageIcon l = new ImageIcon(new ImageIcon("Images/HumanStandRight.png").getImage());
    ImageIcon rj = new ImageIcon(new ImageIcon("Images/HumanJumpLeft.png").getImage());
    ImageIcon lj = new ImageIcon(new ImageIcon("Images/HumanJumpRight.png").getImage());
    ImageIcon[] anirun = {new ImageIcon(new ImageIcon("Images/HumanRunLeft1.png").getImage()), new ImageIcon(new ImageIcon("Images/HumanRunLeft2.png").getImage())};
    ImageIcon[] anirun2 = {new ImageIcon(new ImageIcon("Images/HumanRunRight1.png").getImage()), new ImageIcon(new ImageIcon("Images/HumanRunRight2.png").getImage())};
    public String username, deathString;
    public int[] stats, locations;
    public int x, y, direction = 1, deaths = 0, kills, legion;
    public float veloX = 300F / 60F, speed;
    private float currentVeloX, currentVeloY, aniRunCount;
    private boolean veloAddX = true, veloAddY = true, veloNegX = true, veloNegY = true;
    public boolean jumping, moving, alive = true, collision = false, falling;
    private double decending, decendingSpeed = 0.5, df = 0, dj = 0;
    private int currentJump, jumps = 2, veloY = 15, aniRunSlide;
    public double maxHp = 100, hp;
    private double maxStanma = 80, stanma, stanmaPerSecond = 2;
    private int countDown = 3 * 60, control;
    private Font bigTitle = new Font("SanSerif", Font.BOLD, 100);
    private Font smallFont = new Font("SanSerif", Font.PLAIN, 10);
    
    public Player(int[] locations, int controlID, String username, int[] stats, Float speed, int legion){
        this.locations = locations;
        Random gen = new Random();
        int spawnPoint = gen.nextInt(locations.length / 2);
        spawnPoint++;
        //System.out.println(spawnPoint + " " + ((spawnPoint + spawnPoint) - 2) + " " + ((spawnPoint * 2) - 1));
        x = locations[(spawnPoint + spawnPoint) - 2] - 380;
        y = locations[(spawnPoint * 2) - 1] - 200;
        //System.out.println((x + 380)+ " " + (y + 200));
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
        this.legion = legion;
        if (legion == 1){
            pro = new ImageIcon(new ImageIcon("Images/GoodGuyLogo.png").getImage());
        }
        else if (legion == 2){
            pro = new ImageIcon(new ImageIcon("Images/BadGuyLogo.png").getImage());
        }
        else if (legion == 3){
            pro = new ImageIcon(new ImageIcon("Images/SmartGuyLogo.png").getImage());
        }
    }
    
    public void update(boolean stable, int direction, Map m, Weapon w){
        //System.out.println(veloX + "  " + currentVeloX);
        deathString = null;
        if (hp < 1){
            alive = false;
        }
        if (stanma < maxStanma){
            stanma += stanmaPerSecond / 60;
        }
        respawnCheck(w);
        notMovingVeloX(m);
        if (moving){
            if (this.direction == 2){
                aniRunCount += 1F / 60 * anirun.length;
                if (aniRunCount >= 1){
                    aniRunCount -= 1F;
                    aniRunSlide++;
                }
                if (aniRunSlide + 1 > anirun.length){
                    aniRunSlide = 0;
                    aniRunCount = 0F;
                }
            }
            if (this.direction == 1){
                aniRunCount += 1F / 60 * anirun2.length;
                if (aniRunCount >= 1){
                    aniRunCount -= 1F;
                    aniRunSlide++;
                }
                if (aniRunSlide + 1 > anirun2.length){
                    aniRunSlide = 0;
                    aniRunCount = 0F;
                }
            }
        }
        else{
            aniRunSlide = 0;
            aniRunCount = 0F;
        }
        if (m.sideCollision() != 0){
            collision = true;
        }
        if (moving){
            currentVeloX += veloX;
        }
        if (y > 1000 && alive){
            deathString = username + " fell to his death";
            alive = false;
        }
        for (int e = 0; e < m.potions.length; e++){
            if (m.potions[e].collisionDetection(getBounds()) && m.potions[e].getVisible()){
                hp += m.potions[e].getResources();
                break;
            }
        }
        for (int q = 0; q < m.z.size() && alive; q++){
            hp -= m.z.get(q).takeDamage(getBounds());
            if (m.z.get(q).killer == control){
                kills++;
            }
            if (hp <= 0){
                deathString = username + " was killed by a Zombie";
                hp = 0;
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
            falling = true;
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
                    if (m.topCollision()){
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
        if (legion == 1){
            pro = new ImageIcon(new ImageIcon("Images/GoodGuyLogo.png").getImage());
        }
        else if (legion == 2){
            pro = new ImageIcon(new ImageIcon("Images/BadGuyLogo.png").getImage());
        }
        else if (legion == 3){
            pro = new ImageIcon(new ImageIcon("Images/SmartGuyLogo.png").getImage());
        }
    }
    
    public void thudTest(Map m){
        if (m.fallCollision()){
            decending = 0;
            currentJump = jumps;
            falling = false;
        }
    }
    
    public void notMovingVeloX(Map m){
        if (m.sideCollision() == 0){
            veloAddX = true;
            veloNegX = true;
        }
    }
    
    public void collisionDetection(Map m){
        //System.out.println(direction + " " + x);
        int direction = m.sideCollision();
        if (currentVeloX < 0 && direction == 2){
            veloNegX = false;
            moving = false;
            currentVeloX = 0;
            //System.out.println("You hit the left side");
        }
        else if (currentVeloX > 0 && direction == 1){
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
        Random gen = new Random();
        int spawnPoint = gen.nextInt(locations.length / 2);
        spawnPoint++;
        x = locations[(spawnPoint + spawnPoint) - 2] - 380;
        y = locations[(spawnPoint * 2) - 1] - 200;
        maxHp = 100;
        maxStanma = 80;
        maxHp += (double)stats[2];
        maxStanma += (double)stats[3];
        jumps = 2;
        jumps += stats[1];
        veloX = 300F / 60F;
        veloX += speed / 60F;
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
    
    public void drawPlayer(Component h, Graphics g){
        if (direction == 2 && !moving && !jumping && !falling){
            r.paintIcon(h, g, 380, 200);
        }
        else if (direction == 1 && !moving && !jumping && !falling){
            l.paintIcon(h, g, 380, 200);
        }
        else if (direction == 2 && (jumping || falling)){
            rj.paintIcon(h, g, 380, 200);
        }
        else if (direction == 1 && (jumping || falling)){
            lj.paintIcon(h, g, 380, 200);
        }
        else if (direction == 2 && moving && !jumping && !falling){
            anirun[aniRunSlide].paintIcon(h, g, 380, 200);
        }
        else if (direction == 1 && moving && !jumping && !falling){
            anirun2[aniRunSlide].paintIcon(h, g, 380, 200);
        }
        if (!alive){
            g.setFont(bigTitle);
            g.setColor(Color.cyan);
            g.drawString("" + (((int)countDown / 60) + 1), 380, 200);
        }
    }
    
    public void drawHealthAndStanama(Component h, Graphics g){
        g.setFont(smallFont);
        g.setColor(Color.black);
        g.fillRect(10, 10, 290, 70);
        g.setColor(Color.red);
        g.fillRect(90, 20, 200, 20);
        g.setColor(Color.GREEN);
        g.fillRect(90, 20, (int) ((hp / maxHp) * 200), 20);
        g.setColor(Color.WHITE);
        g.fillOval(10, 10, 70, 70);
        pro.paintIcon(h, g, 20, 20);
        g.drawString((int)hp + "/" + (int)maxHp, 95, 35);
        g.setColor(Color.BLUE);
        g.drawRect(90, 50, 200, 20);
        g.fillRect(90, 50, (int) ((stanma / maxStanma) * 200), 20);
        g.setColor(Color.WHITE);
        g.drawString((int)stanma + "/" + (int)maxStanma, 95, 65);
    }
    
    public void drawStats(Graphics g, double ammo, double ammoMax, Float reloadingDivision, String gun, int r){
        g.setFont(smallFont);
        g.setColor(Color.BLACK);
        g.fillRect(680, 300, 100, 160);
        g.setColor(Color.BLUE);
        g.drawString(username, 685, 315);
        g.drawLine(680, 320, 775, 320);
        g.drawString("K: " + kills + " D: " + deaths + " R: " + r, 685, 335);
        g.drawLine(680, 340, 775, 340);
        g.drawString("X: " + x + " Y: " + y, 685, 355);
        g.drawLine(680, 360, 775, 360);
        g.drawString(gun, 685, 375);
        g.drawLine(680, 380, 775, 380);
        g.drawString("ammo: " + (int)ammo + "/" + (int)ammoMax, 685, 395);
        g.drawRect(685, 405, 80, 10);
        if (reloadingDivision <= 0F){
            g.fillRect(685, 405, (int) ((ammo / ammoMax) * 80), 10);
        }
        else{
            double d = reloadingDivision;
            g.fillRect(685, 405, (int)d, 10);
        }
        g.drawLine(680, 425, 775, 425);
    }
    
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (alive){
            if ((key == KeyEvent.VK_RIGHT && control == 1) || (key == KeyEvent.VK_D && control == 2)){
                if (veloAddX){
                    moving = true;
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
        if (key == KeyEvent.VK_RIGHT && veloX > 0){
            currentVeloX = 0;
            moving = false;
        }
        if (key == KeyEvent.VK_LEFT && veloX < 0){
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