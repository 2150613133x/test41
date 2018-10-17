package com.example.administrator.test4;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class getParam {
    public static String GetValueByKey(String filePath, String key) throws IOException {

         Properties pps = new Properties();
            InputStream in = getParam.class.getResourceAsStream(filePath);
            pps.load(in);
            String value = pps.getProperty(key);
            return value;
        }
    }

