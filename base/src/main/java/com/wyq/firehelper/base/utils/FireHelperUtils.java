package com.wyq.firehelper.base.utils;

import android.content.Context;

import com.wyq.firehelper.base.utils.common.CloseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FireHelperUtils {

    public static String readAssets2String(Context context,String name){
        InputStream is = null;
        try {
            is = context.getResources().getAssets().open(name);
            if(is != null){
                int len = is.available();
                byte[] buffer = new byte[len];
                is.read(buffer);
                return new String(buffer, StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtils.closeIO(is);
        }
        return null;
    }
}
