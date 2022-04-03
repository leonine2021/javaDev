package com.aoli.tank;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tank {
    private int x, y;
    public static final int SPEED = 5;
    private boolean bL, bU, bR, bD;
    private boolean moving = false;
    private Group group;
    TankFrame tf;

    private Dir dir = Dir.R;
    //记录键盘是否按下的变量


    public Tank(int x, int y, Dir dir, Group group, TankFrame tf){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (this.group == Group.GOOD){
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
        }

        if (this.group == Group.BAD){
            switch (dir){
                case L:
                    g.drawImage(ResourceMgr.badTankL, x, y, null);
                    break;
                case U:
                    g.drawImage(ResourceMgr.badTankU, x, y, null);
                    break;
                case R:
                    g.drawImage(ResourceMgr.badTankR, x, y, null);
                    break;
                case D:
                    g.drawImage(ResourceMgr.badTankD, x, y, null);
                    break;
            }
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

    private void fire() {
        tf.add(new Bullet(x, y, dir, group));
    }
}
