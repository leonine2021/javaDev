package com.aoli.tank.chainOfResponsibility;

import com.aoli.tank.AbstractGameObjects;
import com.aoli.tank.Tank;
import com.aoli.tank.Wall;

public class TankWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObjects go1, AbstractGameObjects go2) {
        if(go1 instanceof Tank && go2 instanceof Wall){
            Tank t = (Tank) go1;
            Wall w = (Wall) go2;
            if (t.isLive()){
                if (t.getRect().intersects(w.getRect())){
                    t.back();
                }
            }
        }else if (go1 instanceof Wall && go2 instanceof Tank){
            return collide(go2, go1);
        }
        return true;
    }
}
