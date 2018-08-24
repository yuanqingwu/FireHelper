package com.wyq.firehelper.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class FireUtils {

    public static String readAssets2String(Context context,String name){
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open(name);
            if(is != null){
                int len = is.available();
                byte[] buffer = new byte[len];
                is.read(buffer);
                return new String(buffer,"UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeIO(is);
        }
        return null;
    }
}
