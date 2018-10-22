package com.wyq.firehelper.framework.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.orhanobut.logger.Logger;

/**
 * 清单文件中注册:
 * <receiver android:name=".PowerConnectionReceiver">
 *   <intent-filter>
 *     <action android:name="android.intent.action.ACTION_POWER_CONNECTED"/>
 *     <action android:name="android.intent.action.ACTION_POWER_DISCONNECTED"/>
 *   </intent-filter>
 * </receiver>
 */
public class PowerConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Logger.i("PowerConnectionReceiver"+isCharging+chargePlug);
    }
}
