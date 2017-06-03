/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Arthur
 */
public class Game extends Canvas implements Runnable{
    private BufferedImage icon;
    
    boolean running = true, updating = true;
    JFrame frame;
    Map map;
    JPanel screen = new JPanel();
    Player p;
    Chat c = new Chat(10, 400, 300, 40);
    int[] stats = {2, 0, 30, 50, 1}, spawnPoints;
    Weapon w = new Weapon("Assault Rifle", 12, 40, 1000, 10F, 1F, 0.2F, 1);
    double numSub;
    int round, mode, num;
    Float counter = 0F;
    
    public Game(int mapType, String username, int mode, int num){
        try{
            icon = ImageIO.read(new File("Images/ZombieIcon.png"));
        }catch (Exception e){}
        
        numSub = (double)num;
        this.mode = mode;
        this.num = num;
        if (mapType == 1){
            int[] s = {-150, -200, 100, -200, -100, -50, 50, -50};
            spawnPoints = s;
        }
        if (mapType == 2){
            int[] s = {-550, -100, -250, -50, 350, -150, 600, -50};
            spawnPoints = s;
        }
        if (mapType == 3){
            int[] s = {-50, -50, 0, -50};
            spawnPoints = s;
        }
        p = new Player(spawnPoints, 1, username, stats, 0F, 3);
        map = new Map(p, c, w, mapType, mode, num);
        screen = map;
        //
        frame = new JFrame();
        frame.add(screen);
        frame.setSize(816, 539);
        frame.setTitle("Side Scrolling Game");
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new AL());
        frame.addMouseListener(new ML());
        frame.setIconImage(icon);
    }
    
    public void start(){
        running = true;
    }
    
    public void stop(){
        running = false;
    }
    
    public void run() {
        try{
            long lastTime = System.nanoTime();
            final double amountOfTicks = 60D;
            double ns = 1000000000 / amountOfTicks;
            double delta = 0;

            //Render as fast as the computer and update 60FPS
            while(running){
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                if(delta >= 1){
                    if (updating){
                        update();
                    }
                    else{
                        map.getPaused(updating);
                    }
                    delta--;
                }
                render();
        }
        }catch(Exception e){}
    }
    
    public void update(){
        p.update(map.collision(), map.sideCollision(), map, w);
        map.update(num, updating);
        w.update(p.x, p.y, p.direction);
        w.collsion(map.gbRect);
        if (p.deathString != null){
            c.rotate(p.deathString);
        }
        if (mode == 1){
            counter += 1/60F;
            if (counter >= 1F){
                counter--;
                num--;
            }
            if (num == 0){
                frame.dispose();
            }
        }
        if (mode == 3){
            if (map.rounds == num){
                frame.dispose();
            }
        }
    }
    
    public void render(){
        screen.repaint();
        //frame.add(screen);
    }
    
    public void resume(){
        updating = true;
    }
    
    public void pause(){
        updating = false;
    }
    
    private class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            //System.out.println("Key Pressed");
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F3){
                if (map.showData){
                    map.showData = false;
                }
                else{
                    map.showData = true;
                }
            }
            if (!c.talking && p.alive){
                p.keyPressed(e);
                w.keyPressed(e);
            }
            if (c.talking && !c.checkIfTalking(e)){
                int o = c.talk(e, p.username);
                cheatCodes(o);
            }
            c.checkIfTalking(e);
        }
        
        public void cheatCodes(int o){
            if (o == 1){
                int w = 0;
                for (int q = 0; q < map.z.size(); q++){
                    if (map.z.get(q).alive){
                        w++;
                    }
                }
                c.rotate("ZombiesOnMap: " + w);
            }
            else if (o == 2){
                for (int q = 0; q < map.z.size(); q++){
                    map.z.get(q).alive = false;
                }
            }
            else if (o == 3){
                p.hp = p.maxHp;
            }
            else if (o == 4){
                p.maxHp = 999999999;
                p.hp = p.maxHp;
            }
            else if (o == 5){
                p.respawn(w);
            }
            else if (o == 11){
                int[] stats = {0, 0, -10, +20, 0};
                p.stats = stats;
                p.speed = 50F;
                w.reset("Mini Machine Gun", 4, 60, 500, 12F, 2F, 0.1F, 1);
                p.respawn(w);
            }
            else if (o == 12){
                int[] stats = {-5, 0, +20, 0, 0};
                p.stats = stats;
                p.speed = -100F;
                w.reset("HeavyMachineGun", 6, 120, 400, 12F, 6F, 0.1F, 1);
                p.respawn(w);
            }
            else if (o == 13){
                int[] stats = {+5, +1, -20, 0, 0};
                p.stats = stats;
                p.speed = +100F;
                w.reset("Pistol", 20, 6, 400, 12F, 1F, 0.4F, 1);
                p.respawn(w);
            }
            else if (o == 14){
                int[] stats = {2, 0, 30, 50, 1};
                p.stats = stats;
                p.speed = 0F;
                w.reset("Assault Rifle", 12, 40, 1000, 10F, 1F, 0.2F, 1);
            }
            else if (o == 15){
                int[] stats = {0, 0, 0, 0, 1};
                p.stats = stats;
                p.speed = -50F;
                w.reset("SniperRifle", 50, 4, 5000, 20F, 4F, 1F, 1);
                p.respawn(w);
            }
            else if (o == 16){
                int[] stats = {5, 10, 400, -79, 1};
                p.stats = stats;
                p.speed = 50F;
                w.reset("RayGun", 100, 9999, 9999, 20F, 60F, 0.00000000000001F, 1);
                p.respawn(w);
            }
            else if (o == 21 || o == 22 || o == 23){
                p.legion = o-20;
            }
        }
        public void keyReleased(KeyEvent e){
            p.keyReleased(e);
            w.keyReleased(e);
            //System.out.println("Key Released");
        }
        public void keyTyped(KeyEvent e){
            
        }
    }
    private class ML extends MouseAdapter{
        public void mouseClicked(MouseEvent m) {
            
        }

        public void mouseExited(MouseEvent m) {
            
        }

        public void mouseEntered(MouseEvent m) {
            
        }

        public void mousePressed(MouseEvent m) {
            c.mouseClicked(m);
            if (new Rectangle(700 + 8, 20 + 31, 20, 20).contains(m.getPoint())){
                running = false;
                frame.dispose();
            }
            if (new Rectangle(740 + 8, 20 + 31, 20, 20).contains(m.getPoint()) && updating){
                pause();
            }
            else if (new Rectangle(740 + 8, 20 + 31, 20, 20).contains(m.getPoint()) && !updating){
                resume();
            }
        }

        public void mouseReleased(MouseEvent m) {
            
        }
    }
}