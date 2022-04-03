package com.aoli.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankFrame extends Frame {
    private Tank myTank;
    private Tank enemy;
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;
    private Bullet bullet;

    public TankFrame (){
        this.setTitle("com.aoli.tank.Tank War");
        this.setLocation(500,500);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener()); // observer mode
        myTank = new Tank(100,100, Dir.R, Group.GOOD, this);
        enemy = new Tank(200, 200, Dir.D, Group.BAD, this);
        bullet = new Bullet(100, 100, Dir.D, Group.BAD);
    }

    public void add(Bullet bullet){
        this.bullet = bullet;
    }

    @Override
    public void paint(Graphics g) {
        myTank.paint(g);
        enemy.paint(g);
        bullet.paint(g);
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
