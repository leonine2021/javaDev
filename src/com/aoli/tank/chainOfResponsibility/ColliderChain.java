package com.aoli.tank.chainOfResponsibility;

import com.aoli.tank.AbstractGameObjects;
import com.aoli.tank.PropMgr;

import java.util.ArrayList;
import java.util.List;

public class ColliderChain implements Collider{
    private List<Collider> colliders;

    public ColliderChain(){
        initColliders();
    }

    private void initColliders() {
        colliders = new ArrayList<>();
        String[] colliderNames = PropMgr.get("colliders").split(",");
        for (String name: colliderNames){
            try {
                Class cls = Class.forName("com.aoli.tank.chainOfResponsibility." + name);
                Collider collider = (Collider) (cls.getConstructor().newInstance());
                colliders.add(collider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public boolean collide(AbstractGameObjects go1, AbstractGameObjects go2){
        for(Collider collider: colliders){
            if(!collider.collide(go1, go2)) {
                return false;
            }
        }
        return true;
    }
}
