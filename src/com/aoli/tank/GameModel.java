package com.aoli.tank;

import com.aoli.tank.chainOfResponsibility.ColliderChain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GameModel implements Serializable {

    private Player myTank;
    private List<AbstractGameObjects> objects;
    ColliderChain chain = new ColliderChain();

    Random r = new Random();

    public GameModel() {
        initGameObjects();
    }

    private void initGameObjects() {


        myTank = new Player(50 + r.nextInt(700),50 + r.nextInt(500),
                Dir.values()[r.nextInt(Dir.values().length)],
                Group.values()[r.nextInt(Group.values().length)]);

        objects = new ArrayList<>();
        int tankCount = Integer.parseInt(PropMgr.get("initTankCnt"));
        for (int i = 0; i < tankCount; i++){
            this.add(new Tank(100 + 150*i, 200, Dir.D, Group.BAD));
        }
//        this.add(new Wall(300, 200, 400, 50));
    }

    public void add(AbstractGameObjects gameObject){
        objects.add(gameObject);
    }

    public void paint(Graphics g){

        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("Num objects:" + objects.size(), 10, 50);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < objects.size(); i++) {
            if(!objects.get(i).isLive()) {
                objects.remove(i);
                break;
            }
        }

        for (int i = 0; i < objects.size(); i++) {
            AbstractGameObjects go1 = objects.get(i);
            for (int j = 0; j < objects.size(); j++) {
                AbstractGameObjects go2 = objects.get(j);
                chain.collide(go1, go2);
            }
            if (objects.get(i).isLive()){
                objects.get(i).paint(g);
            }
        }
    }

    public Player getMyTank(){
        return myTank;
    }

    public Tank findTankByUUID(UUID id) {
        for(AbstractGameObjects o:objects){
            if(o instanceof Tank){
                Tank t = (Tank) o;
                if (id.equals(t.getId()))return t;
            }
        }
        return null;
    }
}
