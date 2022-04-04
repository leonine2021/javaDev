package com.aoli.tank.chainOfResponsibility;

import com.aoli.tank.AbstractGameObjects;
import com.aoli.tank.Bullet;
import com.aoli.tank.Wall;

public class BulletWallCollider implements Collider{
    @Override
    public boolean collide(AbstractGameObjects go1, AbstractGameObjects go2) {
        if(go1 instanceof Bullet && go2 instanceof Wall){
            Bullet b = (Bullet) go1;
            Wall w = (Wall) go2;
            if (b.isLive()){
                if (b.getRect().intersects(w.getRect())){
                    b.die();
                    return false;
                }
            }
        }else if (go1 instanceof Wall && go2 instanceof Bullet){
            return collide(go2, go1);
        }
        return true;
    }
}
