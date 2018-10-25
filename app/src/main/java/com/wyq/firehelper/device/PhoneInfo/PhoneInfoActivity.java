package com.wyq.firehelper.device.PhoneInfo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.base.BaseCaseActivity;
import com.wyq.firehelper.utils.BatteryUtils;
import com.wyq.firehelper.utils.CommonUtils;
import com.wyq.firehelper.utils.ConnectivityUtils;

import java.util.List;

import butterknife.BindView;

public class PhoneInfoActivity extends BaseCaseActivity {

    @BindView(R.id.device_activity_phone_info_refresh_bt)
    public Button refreshBt;

    @BindView(R.id.device_activity_phone_info_screen_tv)
    public TextView screenInfoTv;
    @BindView(R.id.device_activity_phone_info_battery_tv)
    public TextView batteryInfoTv;
    @BindView(R.id.device_activity_phone_info_connectivity_tv)
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

    private void refreshData() {
        StringBuilder builder = new StringBuilder();
        builder.append("Screen:\n");
        builder.append("\nwidth:" + CommonUtils.getScreenWidth(this) + " px");
        builder.append("\nheight:" + CommonUtils.getScreenHeight(this) + " px");
        builder.append("\nScreenWidth:" + CommonUtils.getWinWidth(this) + " px");
        builder.append("\nScreenHeight:" + CommonUtils.getWinHeight(this) + " px");
        builder.append("\nNavigationBarHeight:" + CommonUtils.getNavigationBarHeight(this) + " px");
        builder.append("\nStatusBarHeight:" + CommonUtils.getStatusBarHeight(this) + " px");
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
