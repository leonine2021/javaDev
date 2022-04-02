import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        TankFrame tf = new TankFrame();
        tf.setVisible(true);

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
