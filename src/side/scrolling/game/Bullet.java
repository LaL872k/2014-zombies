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
public class Bullet {
    public int x, y, width, height, d, dist, dt;
    Float dtest;
    
    public Bullet(int x, int y, int width, int height, int d, int dist, int dt, Float dtest){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.d = d;
        this.dist = dist;
        this.dt = dt;
        this.dtest = dtest;
    }
}
