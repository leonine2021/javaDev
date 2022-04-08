package com.aoli.tank;

import com.aoli.tank.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

public class Tank extends AbstractGameObjects{
    public static final int SPEED = 5;
    private int x, y;
    private boolean bL, bU, bR, bD;
    private boolean moving = true;
    private Group group;
    private boolean isLive = true;
    private int width, height;
    private Rectangle rect;

    private UUID id;

    public UUID getId() {
        return id;
    }

    private int oldX, oldY;
    private Dir dir = Dir.R;
    private Random random = new Random();

    public Tank(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.moving = msg.isMoving();
        this.group = msg.getGroup();
        this.id = msg.getId();
        oldX = x;
        oldY = y;
        this.width = ResourceMgr.goodTankU.getWidth();
        this.height = ResourceMgr.goodTankU.getHeight();
        rect = new Rectangle(x, y, width, height);
    }

    public Tank(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        oldX = x;
        oldY = y;
        this.width = ResourceMgr.goodTankU.getWidth();
        this.height = ResourceMgr.goodTankU.getHeight();
        rect = new Rectangle(x, y, width, height);
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
    //记录键盘是否按下的变量

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g) {

        if (!this.isLive()) return;

        switch (dir){
            case L:
                g.drawImage(this.group.equals(Group.GOOD)? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.GOOD)? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.GOOD)? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.GOOD)? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }
        move();
        // update rect
        rect.x = x;
        rect.y = y;
    }

    private void move() {
        if (!moving) return;

        oldX = x;
        oldY = y;

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
        boundsCheck();
        randomDir();
        if (random.nextInt(100) > 90) {
            fire();
        }
    }

    private void randomDir() {
        if (random.nextInt(100) > 90) {
            this.dir = Dir.randomDir();
        }
    }

    private void fire() {
        int bx = x + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int by = y + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        TankFrame.INSTANCE.getGm().add(new Bullet(bx, by, dir, group));
    }

    public void die() {
        this.setLive(false);
        TankFrame.INSTANCE.getGm().add(new Explosion(x, y));
    }

    private void boundsCheck() {
        if (x < 0 || x + width > TankFrame.GAME_WIDTH || y < 30 || y + height > TankFrame.GAME_HEIGHT){
            this.back();
        }
    }

    public void back() {
        this.x = oldX;
        this.y = oldY;
    }

    public Rectangle getRect() {
        return rect;
    }
}
