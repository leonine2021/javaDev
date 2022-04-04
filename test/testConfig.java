import com.aoli.tank.PropMgr;

import java.io.IOException;
import java.util.Properties;

public class testConfig {
    public static void main(String[] args) {
        System.out.println(PropMgr.get("initTankCnt"));
    }
}
