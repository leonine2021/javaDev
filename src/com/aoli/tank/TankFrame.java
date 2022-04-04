package com.aoli.tank;

import com.aoli.tank.chainOfResponsibility.BulletTankCollider;
import com.aoli.tank.chainOfResponsibility.BulletWallCollider;
import com.aoli.tank.chainOfResponsibility.Collider;
import com.aoli.tank.chainOfResponsibility.ColliderChain;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame(); // 把Tankframe单例化

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    private Player myTank;
    private List<AbstractGameObjects> objects;

    ColliderChain chain = new ColliderChain();

    private TankFrame (){
        this.setTitle("com.aoli.tank.Tank War");
        this.setLocation(500,500);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener()); // observer mode
        initGameObjects();
    }

    private void initGameObjects() {
        myTank = new Player(100,100, Dir.R, Group.GOOD);
        objects = new ArrayList<>();
        int tankCount = Integer.parseInt(PropMgr.get("initTankCnt"));
        for (int i = 0; i < tankCount; i++){
            this.add(new Tank(100 + 50*i, 200, Dir.D, Group.BAD));
        }
        this.add(new Wall(300, 200, 400, 50));
    }



    public void add(AbstractGameObjects gameObject){
        objects.add(gameObject);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Num objects:" + objects.size(), 10, 50);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            if(!objects.get(i).isLive()) {
                objects.remove(i);
                break;
            }

            AbstractGameObjects go1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObjects go2 = objects.get(j);
                chain.collide(go1, go2);
            }
            if (objects.get(i).isLive()){
                objects.get(i).paint(g);
            }
        }
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
