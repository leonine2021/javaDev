package com.aoli.tank.strategies;

import com.aoli.tank.*;

public class FourDirFire implements FireStrategy{

    @Override
    public void fire(Player p) {
        int bx = p.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int by = p.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        Dir[] dirs = Dir.values();
        for (Dir d: dirs){
            TankFrame.INSTANCE.add(new Bullet(bx, by, d, p.getGroup()));
        }
    }
}
