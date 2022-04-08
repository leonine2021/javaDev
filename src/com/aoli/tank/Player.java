package com.aoli.tank;

import com.aoli.tank.net.Client;
import com.aoli.tank.net.Msg;
import com.aoli.tank.net.TankStartMovingMsg;
import com.aoli.tank.net.TankStopMsg;
import com.aoli.tank.strategies.DefaultFire;
import com.aoli.tank.strategies.FireStrategy;
import com.aoli.tank.strategies.FourDirFire;
import io.netty.bootstrap.Bootstrap;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class Player extends AbstractGameObjects{
    public static final int SPEED = 5;
    private int x, y;
    private boolean bL, bU, bR, bD;
    private boolean moving = false;
    private Group group;
    private boolean isLive = true;
    private Dir dir = Dir.R;

    private UUID id = UUID.randomUUID();
    private FireStrategy strategy = null;

    public Player(int x, int y, Dir dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        // initialize the fire strategy only once from config file
        this.initFireStrategy();
    }

    public UUID getId() {
        return id;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
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

    public void setDir(Dir dir) {
        this.dir = dir;
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

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString(), x, y-10);
        g.setColor(c);

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
        Boolean oldMoving = moving;

        // all keys are released
        if (!bD && !bL && !bU && !bR){
            moving = false;
            Client.INSTANCE.send(new TankStopMsg(this.id, this.x, this.y));
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
            //发送消息到服务器（先判断之前的状态）
            if (!oldMoving) Client.INSTANCE.send(new TankStartMovingMsg(this.id, this.x, this.y, this.dir));
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
