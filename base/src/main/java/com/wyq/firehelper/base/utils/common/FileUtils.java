package com.wyq.firehelper.base.utils.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static com.wyq.firehelper.base.utils.common.CloseUtils.closeIO;


/**
 * @author Uni.W
 * @date 2018/12/14 9:27
 */
public class FileUtils {

    public static byte[] read(File mfile) {
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(mfile));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            return buffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(is);
        }
        return null;
    }



}
