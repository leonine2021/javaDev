package com.aoli.tank.strategies;

import com.aoli.tank.*;
import com.aoli.tank.net.BulletNewMsg;
import com.aoli.tank.net.Client;

public class DefaultFire implements FireStrategy {

    @Override
    public void fire(Player p) {
        int bx = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int by = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;

        Bullet b = new Bullet(p.getId(), bx, by, p.getDir(), p.getGroup());
        TankFrame.INSTANCE.getGm().add(b);

        Client.INSTANCE.send(new BulletNewMsg(b));
    }
}
