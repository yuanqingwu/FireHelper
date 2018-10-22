package com.wyq.firehelper.framework.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 清单文件中注册:
 * <receiver android:name=".BatteryLevelReceiver">
 * <intent-filter>
 *   <action android:name="android.intent.action.ACTION_BATTERY_LOW"/>
 *   <action android:name="android.intent.action.ACTION_BATTERY_OKAY"/>
 *   </intent-filter>
 * </receiver>
 */
public class BatteryLevelReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();


    }
}
