/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Arthur
 */
public class SplitScreenZombie {
    int x, y, startX, startY, direction, veloY = 10, hp = 50, timetillattack, killer;
    public float veloX = 10F / 60F;
    private float currentVeloX, currentVeloY;
    public boolean moving, jumping, moveRight = true, moveLeft = true, alive = true, decendings, loaded = true;
    private double decending, decendingSpeed = 0.5, df = 0, dj = 0;
    
    public SplitScreenZombie(int x, int y){
        this.x = x;
        startX = x;
        this.y = y;
        startY = y;
    }
    
    public void update(SplitScreenMap m, int damage, int control){
        if (alive){
            hp -= damage;
            if (hp <= 0){
                alive = false;
                killer = control;
            }
            if (timetillattack != 0){
                timetillattack--;
                if (timetillattack == 0){
                    loaded = true;
                }
            }
            checkIfMovable(m.gb);
            fallTest(m.gb);
            fall(m.gb);
            move(m.gb);
            ai(m.p1.x, m.p2.x);
            jump(m.gb);
        }
        else{
            respawn();
        }
    }
    
    public void takeDamageUpdate(int damage, int control){
        if (alive){
            hp -= damage;
            if (hp <= 0){
                alive = false;
                killer = control;
            }
        }
    }
    
    public void respawn(){
        killer = 0;
        hp = 50;
        x = startX;
        y = startY;
        alive = true;
    }
    
    public void jump(GrassDirt[] gb){
//        if (jumping){
//            for (int r = 0; r < currentVeloY; r++){
//                y--;
//            }
//            currentVeloY--;
//            if (currentVeloY <= 0){
//                jumping = false;
//            }
//        }
        if (jumping){
            currentVeloY = (float) -dj;
            if (currentVeloY == 0){
                jumping = false;
            }
            else{
                while (true){
                    if (topCollision(gb)){
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
    
    public void move(GrassDirt[] gb){
        if (moving){
            currentVeloX += veloX;
            while (true){
                checkIfMovable(gb);
                if (direction == 2 && moveLeft){
                    x++;
                }
                else if (direction == 1 && moveRight){
                    x--;
                }
                if (currentVeloX >= 1){
                    currentVeloX--;
                }
                else{
                    break;
                }
            }
        }
    }
    
    public void ai(int x, int x2){
        int c1 = (x + 380) - this.x;
        int c2 =  (x2 + 380) - this.x;
        if (c2 - c1 > c1 - c2){
            x = x2;
        }
        if ((x + 380) - this.x > -500 && (x + 380) - this.x < 0 && moveRight){
            moving = true;
            direction = 1;
            currentVeloX = veloX;
        }
        else if ((x + 380) - this.x < 500 && (x + 380) - this.x > 0 && moveLeft){
            moving = true;
            direction = 2;
            currentVeloX = veloX;
        }
        else{
            moving = false;
        }
    }
    
    public void fall(GrassDirt[] gb){
        if (decendings && !jumping){
            decending += decendingSpeed;
            while (true){
                fallTest(gb);
                df++;
                if (df > decending){
                    df = 0;
                    break;
                }
                else{
                    y++;
                }
            }
        }
    }
    
    public int takeDamage(Rectangle rect){
        if (rect.intersects(new Rectangle(x, y, 40, 80)) && loaded){
            loaded = false;
            timetillattack = 60;
            return 5;
        }
        return 0;
    }
    
    public void checkIfMovable(GrassDirt[] gb){
        for (int u = 0; u < gb.length; u++){
            if (gb[u].collisionSideDetection(new Rectangle(x, y, 40, 80)) == 1){
                moving = false;
                jumping = true;
                currentVeloY = -veloY;
                dj = veloY;
                currentVeloX = 0;
                moveLeft = false;
            }
            else if (gb[u].collisionSideDetection(new Rectangle(x, y, 40, 80)) == 2){
                moving = false;
                jumping = true;
                currentVeloY = -veloY;
                dj = veloY;
                currentVeloX = 0;
                moveRight = false;
            }
            else{
                moveRight = true;
                moveLeft = true;
            }
        }
    }
    
    public void fallTest(GrassDirt[] gb){
        for (int q = 0; q < gb.length; q++){
            if (!gb[q].collisionThumpDetection(new Rectangle(x, y, 40, 80))){
                decendings = true;
            }
            else{
                decendings = false;
                decending = 0;
            }
        }
    }
    
    public boolean topCollision(GrassDirt[] gb){
        for (int q = 0; q < gb.length; q++){
            if (gb[q].collisionTopDetection(new Rectangle(x, y, 40, 80))){
                return true;
            }
        }
        return false;
    }
    
    public void drawZombie(Graphics g, int x, int y){
        if (alive){
            g.drawRect(this.x - x, this.y - y, 40, 80);
        }
    }
}