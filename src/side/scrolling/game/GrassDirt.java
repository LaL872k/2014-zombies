/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.*;
import javax.swing.ImageIcon;

public class GrassDirt {
    
    public int x, y;
    final public int width = 50, height = 50;
    private boolean visable = true;
    ImageIcon img = new ImageIcon(new ImageIcon("Images/GrassDirtBlock.png").getImage());
    
    public GrassDirt(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public void drawBlock(Component h, Graphics g, int x, int y){
        if (this.x - x < 800 && this.x - x > 0 - width && this.y - y > 0 - height && this.y - y < 500){
            img.paintIcon(h, g, this.x - x, this.y - y);
        }
    }
    
    public void drawBlockSide(Graphics g, int x, int y){
        if (this.x - x < 800 && this.x - x > 0 - width && this.y - y > 0 - height && this.y - y < 500){
            g.drawRect(this.x - x + 683, this.y - y, width, height);
        }
    }
    
    public boolean collisionDetection(Rectangle rect){
        return getBounds().intersects(rect);
    }
    
    public boolean collisionThumpDetection(Rectangle rect){
        return new Rectangle(x, y - 1, width, height).intersects(rect);
    }
    
    public boolean collisionTopDetection(Rectangle rect){
        return new Rectangle(x, y + height, width, 1).intersects(rect);
    }
    
    public int collisionSideDetection(Rectangle rect){
        if (new Rectangle(x - 1, y + 1, 1, height).intersects(rect)){
            return 1;
        }
        else if (new Rectangle(x + width, y + 1, 1, height).intersects(rect)){
            return 2;
        }
        return 0;
    }
    
    public int collisionSideDetection2(Rectangle rect){
        if (new Rectangle(x - 1, y + 1, 1, height).intersects(rect)){
            return 1;
        }
        else if (new Rectangle(x + width, y + 1, 1, height).intersects(rect)){
            return 2;
        }
        return 0;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
