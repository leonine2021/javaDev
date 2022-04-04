package com.aoli.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame(); // 把Tankframe单例化

    private Player myTank;
    private List<Tank> tanks;
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    private List<Bullet> bullets;
    private List<Explosion> explodes;



    private TankFrame (){
        this.setTitle("com.aoli.tank.Tank War");
        this.setLocation(500,500);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener()); // observer mode
        initGameObjects();
    }

    private void initGameObjects() {
        myTank = new Player(100,100, Dir.R, Group.GOOD);
        bullets = new ArrayList<>();
        tanks = new ArrayList<>();
        explodes = new ArrayList<>();

        for (int i = 0; i < 10; i++){
            tanks.add(new Tank(100 + 50*i, 200, Dir.D, Group.BAD));
        }

    }

    public void add(Bullet bullet){
        this.bullets.add(bullet);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Num Bullets:" + bullets.size(), 10, 50);
        g.drawString("Num Enemies:" + tanks.size(), 10, 100);
        g.drawString("Num explodes:" + explodes.size(), 10, 150);
        g.setColor(c);

        myTank.paint(g);
        for(int i=0; i<tanks.size(); i++){
            if (!tanks.get(i).isLive()){
                tanks.remove(i);
            }else{
                tanks.get(i).paint(g);
            }
        }
        for(int i = 0; i < bullets.size(); i++){
            for (int j = 0; j<tanks.size();j++){
                bullets.get(i).collidesWithTank(tanks.get(j));
            }
            if (!bullets.get(i).isLive()){
                bullets.remove(i);
            }else{
                bullets.get(i).paint(g);
            }
        }
        for(int i=0; i<explodes.size(); i++){
            if (!explodes.get(i).isLive()){
                explodes.remove(i);
            }else{
                explodes.get(i).paint(g);
            }
        }
    }

    public void add(Explosion explode) {
        this.explodes.add(explode);
    }

    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            myTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            myTank.keyReleased(e);
        }
    }

    // 以下代码块是为了解决双缓存线性扫描带来的频闪问题，解决方法为在内存生成一张和屏幕大小一样的虚拟图片，再一次性将整张虚拟图片加载到屏幕上
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }
}
