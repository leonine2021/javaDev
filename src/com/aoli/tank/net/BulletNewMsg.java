package com.aoli.tank.net;

import com.aoli.tank.*;

import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg{
    private int x, y;
    private Dir dir;
    private UUID playerId;
    private UUID id; //bullet id
    private Group group;

    public BulletNewMsg() {
    }
    public BulletNewMsg(Bullet b){
        this.playerId = b.getPlayerId();
        this.id = b.getId();
        this.x = b.getX();
        this.y = b.getY();
        this.dir = b.getDir();
        this.group = b.getGroup();
    }


    @Override
    public byte[] toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeLong(playerId.getMostSignificantBits());
            dos.writeLong(playerId.getLeastSignificantBits());
            dos.writeLong(id.getMostSignificantBits());
            dos.writeLong(id.getLeastSignificantBits());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());

            dos.flush();
            bytes = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(dos != null)
                    dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try{
            this.playerId = new UUID(dis.readLong(), dis.readLong());
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                dis.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle() {
        if(this.playerId.equals(TankFrame.INSTANCE.getGm().getMyTank().getId())) return;
        Bullet b = new Bullet(this.playerId, x, y, dir, group);
        b.setId(this.id);
        TankFrame.INSTANCE.getGm().add(b);

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", playerId=" + playerId +
                ", id=" + id +
                ", group=" + group +
                '}';
    }
}
