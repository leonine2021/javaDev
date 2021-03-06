package com.aoli.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame(); // 把Tankframe单例化

    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 600;

    private GameModel gm = new GameModel();

    private TankFrame (){
        this.setTitle("com.aoli.tank.Tank War");
        this.setLocation(500,500);
        this.setSize(GAME_WIDTH,GAME_HEIGHT);
        this.addKeyListener(new TankKeyListener()); // observer mode
    }

    @Override
    public void paint(Graphics g) {
        gm.paint(g);
    }

    private class TankKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_S) save();
            else if (key == KeyEvent.VK_L) load();
            else gm.getMyTank().keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            gm.getMyTank().keyReleased(e);
        }
    }

    private void save() {
        ObjectOutputStream oos = null;
        try {
            File f = new File("/Users/leo/Documents/dev/tank.dat");
            FileOutputStream fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gm);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (oos!=null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void load() {
        ObjectInputStream ois = null;
        try {
            File f = new File("/Users/leo/Documents/dev/tank.dat");
            FileInputStream fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            this.gm = (GameModel) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public GameModel getGm(){
        return gm;
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
