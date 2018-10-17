package com.example.administrator.test4;

public class Select {

    public static String choice(String brand){
        String path = null;
        switch (brand){
            case  "OPPO":path = "/assets/OppoPub.properties";break;
            case  "华为":path = "/assets/HuaweiPub.properties";break;
            case  "苹果":path = "/assets/ApplePub.properties";break;
        }
        return path;
    }
}
