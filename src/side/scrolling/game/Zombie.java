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
public class Zombie {
    ImageIcon img = new ImageIcon(new ImageIcon("Images/Zombie.png").getImage());
    int x, y, startX, startY, direction, veloY = 10, hp = 50, timetillattack, killer, jumps, maxJumps = 2, lastX;
    int[] locations;
    public float veloX = 10F / 60F;
    private float currentVeloX, currentVeloY, timeTillRandomMovement, mover;
    public boolean moving, jumping, moveRight = true, moveLeft = true, alive = true, decendings, loaded = true, willMove = true, friendlycollision;
    private double decending, decendingSpeed = 0.5, df = 0, dj = 0;
    Random gen = new Random();
    public Zombie(int[] loc){
        locations = loc;
        Random gen = new Random();
        int spawnPoint = gen.nextInt(locations.length / 2);
        spawnPoint++;
        x = locations[(spawnPoint + spawnPoint) - 2];
        y = locations[(spawnPoint * 2) - 1];
        jumps = maxJumps;
    }
    public void update(Map m, int damage, int control, ArrayList<Zombie> z, int array){
        if (alive){
            hp -= damage;
            if (y > 1000){
                alive = false;
            }
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
//            System.out.println("x: " + x + ", lastX: " + lastX + ", jumping: " + jumping);
//            if (lastX == x && !jumping && jumps > 0){
//                moving = false;
//                lastX = x;
//                if (jumps > 0){
//                    jumping = true;
//                    jumps--;
//                    currentVeloY = -veloY;
//                    dj = veloY;
//                }
//                currentVeloX = 0;
//            }
            checkIfMovable(m.gb);
            fallTest(m.gb);
            fall(m.gb);
            move(m.gb);
            ai(m.p.x, m.gb, z, array, m);
            jump(m.gb);
        }
        else{
            killer = 0;
        }
    }
    
    public void respawn(){
        killer = 0;
        hp = 50;
        Random gen = new Random();
        int spawnPoint = gen.nextInt(locations.length / 2);
        spawnPoint++;
        x = locations[(spawnPoint + spawnPoint) - 2];
        y = locations[(spawnPoint * 2) - 1];
        alive = true;
        jumps = maxJumps;
    }
    
    public void jump(GrassDirt[] gb){
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
                fallToDeathTest(gb);
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
    
    public void ai(int x, GrassDirt[] gb, ArrayList<Zombie> z, int numinarray, Map m){
        checkToMove(x, m);
        friendlycollision = false;
        for (int q = 0; q < z.size() && z.get(q).alive; q++){
            if (new Rectangle(this.x + 15, y, 10, 80).intersects(new Rectangle(z.get(q).x + 15, z.get(q).y, 10, 80)) && q != numinarray && !z.get(q).moving && willMove){
                Random gen = new Random();
                friendlycollision = true;
                int w = gen.nextInt(2);
                if (w == 0){
                    moving = true;
                    direction = 1;
                    currentVeloX = veloX;
                }
                else if (w == 1){
                    moving = true;
                    direction = 2;
                    currentVeloX = veloX;
                }
                return;
            }
            else if (new Rectangle(this.x + 15, y, 10, 80).intersects(new Rectangle(z.get(q).x + 15, z.get(q).y, 10, 80)) && q != numinarray && z.get(q).moving && z.get(q).direction == 1 && willMove){
                    friendlycollision = true;
                    moving = true;
                    direction = 2;
                    currentVeloX = veloX;
                    return;
            }
            else if (new Rectangle(this.x + 15, y, 10, 80).intersects(new Rectangle(z.get(q).x + 15, z.get(q).y, 10, 80)) && q != numinarray && z.get(q).moving && z.get(q).direction == 2 && willMove){
                    friendlycollision = true;
                    moving = true;
                    direction = 1;
                    currentVeloX = veloX;
                    return;
            }
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
            if (timeTillRandomMovement <= 0){
                timeTillRandomMovement = 2;
                int p = gen.nextInt(1);
                if (p == 0){
                    mover = 5;
                }
                else if (p == 1){
                    mover = -5;
                }
            }
            
            else if (mover > 0){
                //System.out.println(timeTillRandomMovement);
                checkIfMovable(gb);
                fallToDeathTest(gb);
                if (moveRight){
                    mover--;
                    x--;
                    //System.out.println("llll");
                }
                
            }
            else if (mover < 0){
                //System.out.println(timeTillRandomMovement);
                checkIfMovable(gb);
                fallToDeathTest(gb);
                if (moveLeft){
                    mover++;
                    x++;
                }
            }
            timeTillRandomMovement -= 1F / 60F;
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
        if (rect.intersects(new Rectangle(x, y, 40, 80)) && loaded && alive){
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
                if (jumps > 0 && !jumping){
                    lastX = x;
                    jumping = true;
                    jumps--;
                    dj = veloY;
                }
                currentVeloX = 0;
                moveLeft = false;
            }
            else if (gb[u].collisionSideDetection(new Rectangle(x, y, 40, 80)) == 2){
                moving = false;
                if (jumps > 0 && !jumping){
                    lastX = x;
                    jumping = true;
                    jumps--;
                    dj = veloY;
                }
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
                jumps = maxJumps;
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
    
    public void fallToDeathTest(GrassDirt[] gb){
        for (int q = 0; q < gb.length; q++){
            for (int w = 0; w < gb[q].width; w++){
                if ((gb[q].x + w == x - 1 && direction == 1) || (gb[q].x + w == x + 40 && direction == 2)){
                    if (gb[q].y > y){
                        return;
                    }
                }
            }
        }
        moving = false;
        currentVeloX = 0;
        if (direction == 2){
            moveLeft = false;
        }
        else if (direction == 1){
            moveRight = false;
        }
    }
    public void checkToMove(int x, Map m){
        if (lastX == x && !friendlycollision){
            willMove = false;
        }
        else if (new Rectangle(this.x, y, 40, 80).intersects(new Rectangle(x, m.p.y, 40, 80))){
            willMove = false;
        }
        else{
            willMove = true;
        }
    }
    
    public void healthBar(Graphics g, int x, int y){
        g.setColor(Color.black);
        g.drawRect(this.x - x - 1, this.y - 21 - y, 42, 12);
        g.setColor(Color.red);
        g.fillRect(this.x - x, this.y - 20 - y, 40, 10);
        g.setColor(Color.green);
        double hpd = hp;
        g.fillRect(this.x - x, this.y - 20 - y, (int) ((hpd / 50) * 40), 10);
    }
    
    public void drawZombie(Component h, Graphics g, int x, int y){
        if (alive){
            img.paintIcon(h, g, this.x - x, this.y - y);
            //healthBar(g, x, y);
        }
    }
}