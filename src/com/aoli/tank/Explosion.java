package com.aoli.tank;

import java.awt.*;

public class Explosion extends AbstractGameObjects{
    private int x, y;
    private int width, height;
    private int step = 0;
    private boolean live = true;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public void paint(Graphics g) {
        if(!live) return;
        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step ++;

        if (step >= ResourceMgr.explodes.length){
            this.die();
        }
    }

    private void die() {
        this.live = false;
    }
}
