import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    private int x, y;
    public static final int SPEED = 5;
    private boolean bL, bU, bR, bD;

    private Dir dir = Dir.R;
    //记录键盘是否按下的变量


    public Tank(int x, int y, Dir dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void paint(Graphics g) {
        g.fillRect(x, y, 50, 50);
        move();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
        }
        setMainDir();
    }

    private void setMainDir() {
        if (!bD && !bL && !bU && !bR){
            dir = Dir.STOP;
        }
        if (bD && !bL && !bU && !bR){
            dir = Dir.D;
        }
        if (!bD && bL && !bU && !bR){
            dir = Dir.L;
        }
        if (!bD && !bL && bU && !bR){
            dir = Dir.U;
        }
        if (!bD && !bL && !bU && bR){
            dir = Dir.R;
        }
    }

    private void move() {
        switch (dir){
            case L:
                x -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
        }
        setMainDir();
    }
}
