package com.wyq.firehelper.base.utils.common;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import static android.content.Intent.EXTRA_DOCK_STATE;
import static android.content.Intent.EXTRA_DOCK_STATE_CAR;
import static android.content.Intent.EXTRA_DOCK_STATE_DESK;
import static android.content.Intent.EXTRA_DOCK_STATE_HE_DESK;
import static android.content.Intent.EXTRA_DOCK_STATE_LE_DESK;

public class BatteryUtils {

    private static final String TAG = BatteryUtils.class.getSimpleName();

    public static int getLevel(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        return level;
    }


    public static int getScale(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        return scale;
    }

    /**
     * 获取当前的电量百分比
     *
     * @param context
     * @return
     */
    public static float getPercent(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float) scale;
        return batteryPct;
    }

    /**
     * 获取Android设备是否在充电
     *
     * @param context
     * @return
     */
    public static boolean isCharging(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        return isCharging;
    }

    /**
     * 提取设备是通过 USB 还是交流充电器进行充电
     * boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
     * boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
     *
     * @param context
     * @return
     */
    public static int getChargePlug(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return chargePlug;
    }


    /**
     * 获取当前android设备插接状态
     *
     * @param context
     * @return
     */
    public static boolean isDocked(Context context) {
        //插接状态详情以 extra 形式包含在 ACTION_DOCK_EVENT 操作的粘性广播中。由于它是粘性广播，因此您无需注册 BroadcastReceiver。如下一段代码中所示，您只需调用 registerReceiver()，将 null 作为广播接收器传入。
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_DOCK_EVENT);
        Intent dockStatus = context.registerReceiver(null, ifilter);
        //fixme
        boolean isDocked = false;
        if (dockStatus != null) {
            int dockState = dockStatus.getIntExtra(EXTRA_DOCK_STATE, -1);
            isDocked = dockState != Intent.EXTRA_DOCK_STATE_UNDOCKED;
        } else {
            Log.e(TAG, "dockStatus is null!");
        }

        return isDocked;
    }

    /**
     * 是否为车载基座
     *
     * @param context
     * @return
     */
    public static boolean isCarDock(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_DOCK_EVENT);
        Intent dockStatus = context.registerReceiver(null, ifilter);
        //fixme
        if (dockStatus != null) {
            int dockState = dockStatus.getIntExtra(EXTRA_DOCK_STATE, -1);
            boolean isCar = dockState == EXTRA_DOCK_STATE_CAR;
            return isCar;
        } else {
            return false;
        }
    }


    /**
     * 如果设备已插接，其插接的基座可能为以下四种不同类型之一：
     * <ul>
     * <li>车载基座</li>
     * <li>桌面基座</li>
     * <li>低端（模拟）桌面基座</li>
     * <li>高端（数字）桌面基座</li>
     * <ul/>
     * 请注意，后两种类型从 Android 的 API 级别 11 才开始引入，因此如果您只关注基座类型而不关心其具体为数字还是模拟形式，则最好检查所有三种类型：
     *
     * @param context
     * @return
     */
    public static boolean isDeskDock(Context context) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_DOCK_EVENT);
        Intent dockStatus = context.registerReceiver(null, ifilter);
        //fixme
        if (dockStatus != null) {
            int dockState = dockStatus.getIntExtra(EXTRA_DOCK_STATE, -1);
            boolean isDesk = dockState == EXTRA_DOCK_STATE_DESK ||
                    dockState == EXTRA_DOCK_STATE_LE_DESK ||
                    dockState == EXTRA_DOCK_STATE_HE_DESK;
            return isDesk;
        } else {
            return false;
        }
    }
}
