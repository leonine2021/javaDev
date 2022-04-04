package com.aoli.tank;

import java.awt.*;

public class Bullet extends AbstractGameObjects{
    public static final int SPEED = 10;
    private int x, y;
    private Dir dir;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private Group group;
    private boolean isLive = true;

    private Rectangle rect;
    private int w = ResourceMgr.bulletU.getWidth();
    private int h = ResourceMgr.bulletU.getHeight();

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.rect = new Rectangle(x, y, w, h);
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void paint(Graphics g) {
        switch (dir) {
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
        }
        move();
        // update the rect
        rect.x = x;
        rect.y = y;
    }

    private void move() {
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
    }

    public void collidesWithTank(Tank tank) {

    }

    public Rectangle getRect(){
        return rect;
    }

    private void boundsCheck() {
        if (x < 0 || x > TankFrame.GAME_WIDTH || y < 30 || y > TankFrame.GAME_HEIGHT){
            isLive = false;
        }
    }

    public void die(){
        this.setLive(false);
    }


}
