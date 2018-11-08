package com.wyq.firehelper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 每当连接详情发生变化时，ConnectivityManager 便会广播 CONNECTIVITY_ACTION ("android.net.conn.CONNECTIVITY_CHANGE") 操作。您可以在清单文件中注册一个广播接收器，以便侦听这些变化和相应地恢复（或暂停）后台更新。
 *
 * <p>设备的连接变化可能非常频繁—您每次在移动数据与 Wi-Fi 之间切换时都会触发该广播。 因此，最好只在您之前暂停过更新或下载时监控该广播，以便恢复这些更新或下载。通常，只要在开始更新前检查互联网连接即已足够，如果没有任何连接，则再暂停其他更新直至连接恢复。
 */
public class ConnectivityUtils {

    /**
     * 查询活动网络并确定其是否连入了互联网
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /**
     * 设备连接可由移动数据、WiMAX、Wi-Fi 和以太网连接提供。
     * <p>例如：boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
     *
     * @param context
     * @return
     */
    public static int getActiveNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork == null ? 0 : activeNetwork.getType();
    }


}
