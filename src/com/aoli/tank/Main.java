package com.aoli.tank;

import com.aoli.tank.net.Client;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        TankFrame.INSTANCE.setVisible(true);
//        new Thread(()->new Audio("audio/war1.wav").play()).start();

        new Thread(()->{
            for (;;){
                try{
                    TimeUnit.MILLISECONDS.sleep(25);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
                TankFrame.INSTANCE.repaint();
            }
        }).start();

        Client.INSTANCE.connect();
    }


}
