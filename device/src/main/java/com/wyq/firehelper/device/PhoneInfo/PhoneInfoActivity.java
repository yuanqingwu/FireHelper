package com.wyq.firehelper.device.PhoneInfo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.base.BaseCaseActivity;
import com.wyq.firehelper.base.article.ArticleConstants;
import com.wyq.firehelper.base.utils.common.BatteryUtils;
import com.wyq.firehelper.base.utils.common.ConnectivityUtils;
import com.wyq.firehelper.base.utils.common.ScreenUtils;
import com.wyq.firehelper.device.R;
import com.wyq.firehelper.device.R2;

import java.util.List;

import butterknife.BindView;

public class PhoneInfoActivity extends BaseCaseActivity {

    @BindView(R2.id.device_activity_phone_info_refresh_bt)
    public Button refreshBt;

    @BindView(R2.id.device_activity_phone_info_screen_tv)
    public TextView screenInfoTv;
    @BindView(R2.id.device_activity_phone_info_battery_tv)
    public TextView batteryInfoTv;
    @BindView(R2.id.device_activity_phone_info_connectivity_tv)
    public TextView connectivityinfoTv;

    @Override
    protected int attachLayoutRes() {
        return R.layout.device_activity_phone_info;
    }

    @Override
    public String getToolBarTitle() {
        return "PhoneInfo";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("PhoneInfo");
    }

    @Override
    public void initView() {
        refreshBt.setText("refresh");
        refreshBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        refreshData();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void refreshData() {
        StringBuilder builder = new StringBuilder();
        builder.append("Screen:\n");
        builder.append("\nsize:" + ScreenUtils.getScreenSizeInch(this)+" inch");
        builder.append("\ndensity:" + ScreenUtils.getDensity(this));
        builder.append("\nXdpi:" + ScreenUtils.getXdpi(this) + " dpi");
        builder.append("\nYdpi:" + ScreenUtils.getYdpi(this) + " dpi");
        builder.append("\nwidth:" + ScreenUtils.getWidthPX(this) + " px");
        builder.append("\nheight:" + ScreenUtils.getHeightPX(this) + " px");
        builder.append("\nScreenWidth:" + ScreenUtils.getScreenWidthPX(this) + " px");
        builder.append("\nScreenHeight:" + ScreenUtils.getScreenHeightPX(this) + " px");
        builder.append("\nNavigationBarHeight:" + ScreenUtils.getNavigationBarHeight(this) + " px");
        builder.append("\nStatusBarHeight:" + ScreenUtils.getStatusBarHeight(this) + " px");
        screenInfoTv.setText(builder.toString());

        StringBuilder batteryInfo = new StringBuilder();
        batteryInfo.append("Battery:\n");
        batteryInfo.append("\nscale:" + BatteryUtils.getScale(this));
        batteryInfo.append("\nlevel:" + BatteryUtils.getLevel(this));
        batteryInfo.append("\npercent:" + BatteryUtils.getPercent(this));
        batteryInfo.append("\nisCharging:" + BatteryUtils.isCharging(this));
        batteryInfo.append("\nChargePlug:" + convertChargePlug2Str(BatteryUtils.getChargePlug(this)));
        batteryInfo.append("\nisDocked:" + BatteryUtils.isDocked(this));
        batteryInfo.append("\nisCarDock:" + BatteryUtils.isCarDock(this));
        batteryInfo.append("\nisDeskDock:" + BatteryUtils.isDeskDock(this));
        batteryInfoTv.setText(batteryInfo.toString());

        StringBuilder connectivityInfo = new StringBuilder();
        connectivityInfo.append("Connectivity\n");
        connectivityInfo.append("\nisConnected:" + ConnectivityUtils.isConnected(this));
        connectivityInfo.append("\nActiveNetworkType:" + convertNetType2Str(ConnectivityUtils.getActiveNetworkType(this)));
        connectivityinfoTv.setText(connectivityInfo.toString());

        String path = getCacheDir().getAbsolutePath()+"\n" +
//                    getDataDir().getAbsolutePath()+"\n" +
                getFilesDir().getAbsolutePath() + "\n" + getCodeCacheDir().getAbsolutePath() + "\n" +
                getExternalCacheDir().getAbsolutePath()+"\n" +
                getNoBackupFilesDir().getAbsolutePath()+"\n" +
                getObbDir().getAbsolutePath()+"\n";
        Logger.i(path);

    }

    private String convertChargePlug2Str(int chargePlug) {
        String res = "";
        switch (chargePlug) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                res = "BATTERY_PLUGGED_AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                res = "BATTERY_PLUGGED_USB";
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                res = "BATTERY_PLUGGED_WIRELESS";
                break;
            default:
                break;
        }
        return res;
    }

    private String convertNetType2Str(int type) {
        String typeStr = "";
        switch (type) {
            case ConnectivityManager.TYPE_MOBILE:
                typeStr = "TYPE_MOBILE";
                break;
            case ConnectivityManager.TYPE_WIFI:
                typeStr = "TYPE_WIFI";
                break;
            case ConnectivityManager.TYPE_MOBILE_DUN:
                typeStr = "TYPE_MOBILE_DUN";
                break;
            case ConnectivityManager.TYPE_WIMAX:
                typeStr = "TYPE_WIMAX";
                break;
            case ConnectivityManager.TYPE_BLUETOOTH:
                typeStr = "TYPE_BLUETOOTH";
                break;
            case ConnectivityManager.TYPE_DUMMY:
                typeStr = "TYPE_DUMMY";
                break;
            case ConnectivityManager.TYPE_ETHERNET:
                typeStr = "TYPE_ETHERNET";
                break;
            case ConnectivityManager.TYPE_VPN:
                typeStr = "TYPE_VPN";
                break;
            default:
                break;
        }
        return typeStr;
    }


    public static void instance(Context context) {
        context.startActivity(new Intent(context, PhoneInfoActivity.class));
    }
}
