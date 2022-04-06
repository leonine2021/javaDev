package com.aoli.tank.strategies;

import com.aoli.tank.Player;

import java.io.Serializable;

public interface FireStrategy extends Serializable {
    public void fire(Player p);
}
