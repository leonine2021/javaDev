package com.aoli.tank;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        TankFrame tf = TankFrame.INSTANCE;
        tf.setVisible(true);
        new Thread(()->new Audio("audio/war1.wav").play()).start();
        for (;;){
            try{
                TimeUnit.MILLISECONDS.sleep(25);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            tf.repaint();
        }
    }


}
