/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

/**
 *
 * @author Arthur
 */
public class Countdown implements Runnable{
    float totaltime, currenttime = 0f;
    Thread loop;
    boolean running = true;
    SideScrollingGame game;
            
    public Countdown(float totaltime){
        this.totaltime = totaltime;
        
        loop = new Thread(this);
        loop.start();
    }
    
    public Countdown(float totaltime, SideScrollingGame game){
        this.totaltime = totaltime;
        this.game = game;
        
        loop = new Thread(this);
        loop.start();
    }
    
    public void run(){
        try{
            while(running){
                //*Updating only 60 times a second
                double amountOfTicks = 60D;
                long lastTime = System.nanoTime();
                double ns = 1000000000 / amountOfTicks;
                double delta = 0;

                //Render as fast as the computer and update 60FPS
                while(running){
                    long now = System.nanoTime();
                    delta += (now - lastTime) / ns;
                    lastTime = now;
                    if(delta >= 1){
                       currenttime += 1/60f;
                       if (currenttime > totaltime){
                           running = false;
                           enchance();
                       }
                       delta--;
                    }
                }
            }
        }catch(Exception e){}
    }
    
    public void enchance(){
        if (finished()){
            game.panel1se();
        }
    }
    
    public boolean finished(){
        if (currenttime > totaltime){
           return true;
       }
        return false;
    }
}
