/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
public class Chat extends JPanel{
    String currentText = "";
    String lastText1 = "";
    String lastText2 = "";
    String lastText3 = "";
    String lastText4 = "";
    int x, y, width, height;
    boolean talking;
    private Font text = new Font("SanSerif", Font.PLAIN, 15);
    
    public Chat(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void render(Graphics g){
        g.setFont(text);
        g.setColor(Color.BLACK);
        if (talking){
            g.setColor(Color.WHITE);
        }
        g.drawRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawString(currentText, x + 5, y  + (height / 2));
        g.drawString(lastText1, x, y - 10);
        g.drawString(lastText2, x, y - 25);
        g.drawString(lastText3, x, y - 40);
        g.drawString(lastText4, x, y - 55);
    }
    
    public void rotate(String newtext){
        lastText4 = lastText3;
        lastText3 = lastText2;
        lastText2 = lastText1;
        lastText1 = newtext;
    }
    public boolean checkIfTalking(KeyEvent e){
        int key = e.getKeyCode();
        if ((key == KeyEvent.VK_T) && !talking){
            talking = true;
            return true;
        }
        else if (key == KeyEvent.VK_SLASH && !talking){
            talking = true;
            currentText += "/";
            return true;
        }
        return false;
    }
    
    public void mouseClicked(MouseEvent m){
        if (new Rectangle(x + 8, y + 31, width, height).contains(m.getPoint())){
            talking = true;
        }
    }
    
    public int cheatCodes(String text){
        if (text.contains("zombiecount")){
            return 1;
        }
        else if (text.contains("zombiegun")){
            try{
                text = text.replaceAll("/zombiegun","");
                text = text.replaceAll(" ","");
                return (Integer.parseInt(text) + 10);
            }catch(Exception e){}
        }
        else if (text.contains("nuclearwar")){
            return 2;
        }
        else if (text.contains("zombiefood")){
            return 3;
        }
        else if (text.contains("changelogo")){
            try{
                text = text.replaceAll("/changelogo","");
                text = text.replaceAll(" ","");
                return (Integer.parseInt(text) + 20);
            }catch(Exception e){}
        }
        else if (text.contains("raygunofdeath")){
            return 16;
        }
        else if (text.contains("zombieopstats")){
            return 4;
        }
        else if (text.contains("zombiebrunch")){
            return 5;
        }
        return 0;
    }
    
    public int talk(KeyEvent e, String username){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER){
            if (!currentText.contains("/")){
                rotate("(" + username + ") " + currentText);
            }
            else{
                int a = cheatCodes(currentText);
                currentText = "";
                talking = false;
                return a;
            }
            currentText = "";
            talking = false;
        }
        else if (key == KeyEvent.VK_ESCAPE){
            currentText = "";
            talking = false;
        }
        else if (key == KeyEvent.VK_BACK_SPACE){
            char[] getTextChar = currentText.toCharArray();
            if (getTextChar.length > 0){
                char[] correctForm = new char[getTextChar.length - 1];
                for (int p = 0; p < correctForm.length; p++){
                    correctForm[p] = getTextChar[p];
                }
                String newText = String.valueOf(correctForm);
                currentText = newText;
            }
        }
        else{
            char letter = e.getKeyChar();
            currentText += letter;
            currentText = currentText.replaceAll("￿","");
        }
        return 0;
    }
}