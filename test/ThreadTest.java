import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class ThreadTest {
    @Test
    public void testThread(){
        new TT().start();
        new Thread(new C()).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("anonymous");
            }
        }).start();
        new Thread(()->{
            System.out.println("hello");
        }).start();
    }

}
class TT extends Thread{
    @Override
    public void run() {
        System.out.println("TT");
    }
}

class C implements Runnable{
    @Override
    public void run() {
        System.out.println("C");
    }
}
