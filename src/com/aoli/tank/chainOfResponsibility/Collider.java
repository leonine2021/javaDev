package com.aoli.tank.chainOfResponsibility;

import com.aoli.tank.AbstractGameObjects;

import java.io.Serializable;

//负责碰撞两个游戏物体
public interface Collider extends Serializable {
    // return true, chain go on, return false, chain break
    public boolean collide(AbstractGameObjects go1, AbstractGameObjects go2);
}
