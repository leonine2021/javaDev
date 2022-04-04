package com.aoli.tank.chainOfResponsibility;

import com.aoli.tank.AbstractGameObjects;
import com.aoli.tank.Bullet;
import com.aoli.tank.ResourceMgr;
import com.aoli.tank.Tank;

import java.awt.*;

public class BulletTankCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObjects go1, AbstractGameObjects go2) {
        if (go1 instanceof Bullet && go2 instanceof Tank){
            Bullet b = (Bullet) go1;
            Tank t = (Tank) go2;
            if (!b.isLive() || !t.isLive()) return false;
            if (b.getGroup() == t.getGroup()) return true;
            Rectangle rectTank = t.getRect();
            if (b.getRect().intersects(rectTank)){
                b.die();
                t.die();
                return false;
            }
        }else if (go1 instanceof Tank && go2 instanceof Bullet){
            return collide(go2, go1);
        }
        return true;
    }
}
