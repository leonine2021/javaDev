package com.aoli.tank;

import com.aoli.tank.strategies.DefaultFire;
import com.aoli.tank.strategies.FireStrategy;
import com.aoli.tank.strategies.FourDirFire;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    public static final int SPEED = 5;
    private int x, y;
    private boolean bL, bU, bR, bD;
    private boolean moving = false;
    private Group group;
    private boolean isLive = true;
    private Dir dir = Dir.R;

    public Player(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        // initialize the fire strategy only once from config file
        this.initFireStrategy();
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
//记录键盘是否按下的变量

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public void paint(Graphics g) {

        if (!this.isLive()) return;

        switch (dir){
            case L:
                g.drawImage(ResourceMgr.goodTankL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.goodTankU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.goodTankR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.goodTankD, x, y, null);
                break;
        }
        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
        }
        setMainDir();
    }

    private void setMainDir() {
        // all keys are released
        if (!bD && !bL && !bU && !bR){
            moving = false;
        }
        else {
            // any key is pressed
            moving = true;
            if (bD && !bL && !bU && !bR) {
                dir = Dir.D;
            }
            if (!bD && bL && !bU && !bR) {
                dir = Dir.L;
            }
            if (!bD && !bL && bU && !bR) {
                dir = Dir.U;
            }
            if (!bD && !bL && !bU && bR) {
                dir = Dir.R;
            }
        }
    }

    private void move() {
        if (!moving) return;
        switch (dir){
            case L:
                x -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
        }
        setMainDir();
    }

    private FireStrategy strategy = null;
    private void initFireStrategy(){
        String className = PropMgr.get("tankFireStrategy");
        try {
            Class cls = Class.forName("com.aoli.tank.strategies." + className);
            strategy = (FireStrategy) (cls.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fire() {
        strategy.fire(this);
    }
    public void die() {
        this.setLive(false);
    }
}
