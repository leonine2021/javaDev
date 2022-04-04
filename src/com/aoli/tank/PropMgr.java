package com.aoli.tank;

import java.io.IOException;
import java.util.Properties;

public class PropMgr {
    private static Properties props;

    static {
        try {
            props = new Properties();
            props.load(PropMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key){
        return (String) props.get(key);
    }
}
