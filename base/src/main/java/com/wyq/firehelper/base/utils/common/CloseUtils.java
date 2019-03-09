package com.wyq.firehelper.base.utils.common;
import java.io.Closeable;
import java.io.IOException;

public class CloseUtils {
    private static final String TAG = "CloseUtils";

    /**
     * Close the io stream.
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
