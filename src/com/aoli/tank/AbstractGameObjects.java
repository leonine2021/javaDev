package com.aoli.tank;

import java.awt.*;
import java.io.Serializable;

public abstract class AbstractGameObjects implements Serializable {
    public abstract void paint(Graphics g);

    public abstract boolean isLive();
}
