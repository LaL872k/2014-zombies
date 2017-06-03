/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package side.scrolling.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SideScrollingGame implements ActionListener{
    ImageIcon img = new ImageIcon(new ImageIcon("Images/Back.png").getImage());
    ImageIcon img2 = new ImageIcon(new ImageIcon("Images/ZombieMenuScreen.png").getImage());
    ImageIcon aellc = new ImageIcon(new ImageIcon("Images/AELLC.png").getImage());
    Thread t;
    int gamemode, mode = 2, intReturn;
    Color background = new Color(50, 130, 50);
    //
    Font font = new Font("Times New Roman", Font.PLAIN, 15);
    JFrame frame = new JFrame();
    JTextField highscoresdisplay = new  JTextField();
    JTextField username = new JTextField("Player");
    JTextField time = new JTextField("300");
    JTextField round = new JTextField("30");
    JCheckBox timeBox = new JCheckBox();
    JCheckBox foreverBox = new JCheckBox();
    JCheckBox roundBox = new JCheckBox();
    //
    JPanel currentPanel = new JPanel();
    JPanel lastPanel = new JPanel();
    JPanel panel = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    //
    JLabel zombie = new JLabel(img2);
    JLabel gameMode = new JLabel("Select Game Mode");
    JLabel mapChooser = new JLabel("Please select a map to start");
    JLabel userName = new JLabel("Username:");
    JLabel timeLabel = new JLabel("Time:");
    JLabel foreverLabel = new JLabel("Forever");
    JLabel roundLabel = new JLabel("Round Limits:");
    //
    JButton play = new JButton("Play");
    JButton playSinglePlayer = new JButton("Single Player");
    JButton playMultiPlayer = new JButton("Multi Player (Not Working)");
    JButton quit = new JButton("Quit");
    JButton controls = new JButton("Control");
    JButton options = new JButton("Options");
    JButton back = new JButton(img);
    JButton map1 = new JButton("Fort Ticanderoga");
    JButton map2 = new JButton("Zombie Farm Land");
    JButton map3 = new JButton("Smiley Face");
    //
    private BufferedImage icon;
    //
    private Countdown count;
    private JLabel l;
    
    public SideScrollingGame(){
        try{
            icon = ImageIO.read(new File("Images/ZombieIcon.png"));
        }catch (Exception e){}
        //
        gameMode.setFont(font);
        gameMode.setBounds(280, 100, 200, 50);
        //
        zombie.setBounds(50, 0, img2.getIconWidth(), img2.getIconHeight());
        //
        mapChooser.setFont(font);
        mapChooser.setBounds(170, 20, 200, 50);
        //
        username.setFont(font);
        username.setBounds(230, 160, 200, 20);
        //
        userName.setFont(font);
        userName.setBounds(150, 145, 100, 50);
        //
        time.setFont(font);
        time.setBounds(120, 190, 50, 20);
        //
        timeLabel.setFont(font);
        timeLabel.setBounds(80, 190, 50, 20);
        //
        roundLabel.setFont(font);
        roundLabel.setBounds(290, 190, 100, 20);
        //
        round.setFont(font);
        round.setBounds(380, 190, 50, 20);
        //
        foreverLabel.setFont(font);
        foreverLabel.setBounds(210, 190, 50, 20);
        //
        timeBox.setBounds(50, 190, 20, 20);
        timeBox.addActionListener(this);
        foreverBox.setBounds(180, 190, 20, 20);
        foreverBox.addActionListener(this);
        foreverBox.setSelected(true);
        roundBox.setBounds(260, 190, 20, 20);
        roundBox.addActionListener(this);
        //
        map1.setFont(font);
        map1.setBounds(50, 60, 400, 25);
        map1.addActionListener(this);
        //
        map2.setFont(font);
        map2.setBounds(50, 90, 400, 25);
        map2.addActionListener(this);
        //
        map3.setFont(font);
        map3.setBounds(50, 120, 400, 25);
        map3.addActionListener(this);
        //
        play.setFont(font);
        play.setBounds(50, 140, 400, 50);
        play.addActionListener(this);
        //
        back.setFont(font);
        back.setBounds(5, 5, 20, 20);
        back.addActionListener(this);
        //
        controls.setFont(font);
        controls.setBounds(50, 175, 198, 40);
        controls.addActionListener(this);
        //
        options.setFont(font);
        options.setBounds(252, 175, 198, 40);
        options.addActionListener(this);
        //
        playSinglePlayer.setFont(font);
        playSinglePlayer.setBounds(20, 80, 200, 50);
        playSinglePlayer.addActionListener(this);
        //
        playMultiPlayer.setFont(font);
        playMultiPlayer.setBounds(20, 140, 200, 50);
        playMultiPlayer.addActionListener(this);
        //
        quit.setFont(font);
        quit.setBounds(50, 200, 400, 50);
        quit.addActionListener(this);
        //
        highscoresdisplay.setEditable(false);
        highscoresdisplay.setBounds(0, 0, 450, 300);
        //
        panel.setLayout(null);
        panel.setVisible(true);
        l = new JLabel();
        l.setBounds(0, 0, 770, 510);
        l.setIcon(aellc);
        panel.add(l);
        panel.setBounds(0, 0, 770, 510);
        panel.setBackground(background);
        //
        panel2.setLayout(null);
        panel2.add(playSinglePlayer);
        panel2.add(playMultiPlayer);
        panel2.add(gameMode);
        panel2.setVisible(false);
        panel2.setBounds(0, 0, 500, 300);
        panel2.setBackground(background);
        //
        panel3.setLayout(null);
        panel3.add(mapChooser);
        panel3.add(username);
        panel3.add(userName);
        panel3.add(time);
        panel3.add(timeLabel);
        panel3.add(timeBox);
        panel3.add(foreverLabel);
        panel3.add(foreverBox);
        panel3.add(round);
        panel3.add(roundLabel);
        panel3.add(roundBox);
        panel3.add(map1);
        panel3.add(map2);
        panel3.add(map3);
        panel3.setVisible(false);
        panel3.setBounds(0, 0, 500, 300);
        panel3.setBackground(background);
        //
        frame.add(panel3);
        frame.add(panel2);
        frame.add(panel);
        frame.setTitle("Launcher");
        frame.setSize(770, 510);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(icon);
        //
        count = new Countdown(5f, this);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");                 

        } catch (Exception e) {

        }

    }
    
    public void panel1se(){
        panel.remove(l);
        panel.add(zombie);
        panel.add(play);
        panel.add(quit);
        //panel.add(options);
        //panel.add(controls);
        panel.setBounds(0, 0, 500, 300);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
    }
    
    public void reset(){
        panel.setVisible(false);
        panel2.setVisible(false);
        panel3.setVisible(false);
    }
    
    public void reset2(){
        foreverBox.setSelected(false);
        roundBox.setSelected(false);
        timeBox.setSelected(false);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (mode == 1){
            intReturn = Integer.parseInt(time.getText());
        }
        else if (mode == 3){
            intReturn = Integer.parseInt(round.getText());
        }
        
        else{
            intReturn = 0;
        }
        if (e.getSource() == play){
            lastPanel = panel;
            reset();
            panel2.setVisible(true);
            panel2.add(back);
        }
        if (e.getSource() == playSinglePlayer){
            gamemode = 1;
            lastPanel = panel2;
            reset();
            panel3.setVisible(true);
            panel3.add(back);
        }
        if (e.getSource() == playMultiPlayer){
            gamemode = 2;
            lastPanel = panel2;
            reset();
            panel3.setVisible(true);
            panel3.add(back);
        }
        if (e.getSource() == quit){
            frame.dispose();
            System.exit(0);
        }
        if (e.getSource() == back){
            reset();
            if (lastPanel == panel){
                panel.setVisible(true);
            }
            else if (lastPanel == panel2){
                panel.setVisible(true);
            }
        }
        if (e.getSource() == map1){
            if (gamemode == 1){
                t = new Thread(new Game(1, username.getText(), mode, intReturn));
                t.start();
            }
        }
        if (e.getSource() == map2){
            if (gamemode == 1){
                t = new Thread(new Game(2, username.getText(), mode, intReturn));
                t.start();
            }
        }
        if (e.getSource() == map3){
            if (gamemode == 1){
                t = new Thread(new Game(3, username.getText(), mode, intReturn));
                t.start();
            }
        }
        if (e.getSource() == timeBox){
            reset2();
            timeBox.setSelected(true);
            mode = 1;
        }
        if (e.getSource() == foreverBox){
            reset2();
            foreverBox.setSelected(true);
            mode = 2;
        }
        if (e.getSource() == roundBox){
            reset2();
            roundBox.setSelected(true);
            mode = 3;
        }
        frame.repaint();
    }
    
    public static void main(String[] args) {
        new SideScrollingGame();
    }
}