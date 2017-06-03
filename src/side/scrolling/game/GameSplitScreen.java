/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Arthur
 */
public class GameSplitScreen extends Canvas implements Runnable{
    boolean running = true;
    JFrame frame;
    SplitScreenMap map;
    JPanel screen = new JPanel();
    SplitScreenPlayer p;
    SplitScreenPlayer p2;
    Chat c = new Chat(10, 400, 300, 40);
    int[] stats = {+5, +1, -20, 0, 0};
    Weapon w = new Weapon("Pistol", 20, 6, 400, 8F, 1F, 0.4F, 1);
    int[] stats2 = {0, 0, +20, 0, 0 };
    Weapon w2 = new Weapon("HeavyMachineGun", 6, 300, 400, 12F, 6F, 0.1F, 2);
    
    public GameSplitScreen(){
        p = new SplitScreenPlayer(0 - 380, -80 - 200, 1, "Invincible_83", stats, +100F);
        p2 = new SplitScreenPlayer(0 - 380, -80 - 200, 2, "Hammer_55", stats2, 0F);
        map = new SplitScreenMap(p, p2, c, w, w2);
        screen = map;
        //
        frame = new JFrame();
        frame.add(screen);
        frame.setSize(1366, 539);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Side Scrolling Game");
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new AL());
        frame.addMouseListener(new ML());
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
                    update();
                    delta--;
                }
                render();
        }
        }catch(Exception e){}
    }
    
    public void update(){
        p.update(map.collision(1), map.sideCollision(1), map, w);
        p2.update(map.collision(2), map.sideCollision(2), map, w2);
        map.update();
        w.update(p.x, p.y, p.direction);
        w.collsion(map.gbRect);
        w2.update(p2.x, p2.y, p2.direction);
        w2.collsion(map.gbRect);
    }
    
    public void render(){
        screen.repaint();
        //frame.add(screen);
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
            if (!c.talking){
                p2.keyPressed(e);
                p.keyPressed(e);
                w.keyPressed(e);
                w2.keyPressed(e);
            }
            if (c.talking && !c.checkIfTalking(e)){
                c.talk(e, p.username);
            }
            c.checkIfTalking(e);
        }
        public void keyReleased(KeyEvent e){
            p.keyReleased(e);
            p2.keyReleased(e);
            w.keyReleased(e);
            w2.keyReleased(e);
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
        }

        public void mouseReleased(MouseEvent m) {

            
        }
    }
}