package com.aoli.tank.chainOfResponsibility;

import com.aoli.tank.AbstractGameObjects;
import com.aoli.tank.Tank;

public class TankTankCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObjects go1, AbstractGameObjects go2) {
        if(go1!=go2 && go1 instanceof Tank && go2 instanceof Tank){
            Tank t1 = (Tank) go1;
            Tank t2 = (Tank) go2;
            if (t1.isLive() && t2.isLive()){
                if (t1.getRect().intersects(t2.getRect())){
                    t1.back();
                    t2.back();
                }
            }
        }
        return true;
    }
}
