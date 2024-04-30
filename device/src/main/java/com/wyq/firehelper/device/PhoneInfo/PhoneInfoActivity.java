package com.wyq.firehelper.device.PhoneInfo;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.base.BaseCaseActivity;
import com.wyq.firehelper.base.utils.common.BatteryUtils;
import com.wyq.firehelper.base.utils.common.ConnectivityUtils;
import com.wyq.firehelper.base.utils.common.ScreenUtils;
import com.wyq.firehelper.device.databinding.DeviceActivityPhoneInfoBinding;

import java.util.List;
public class PhoneInfoActivity extends BaseCaseActivity {

    public Button refreshBt;

    public TextView screenInfoTv;
    public TextView batteryInfoTv;
    public TextView connectivityinfoTv;
    public TextView appInfoTv;

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DeviceActivityPhoneInfoBinding.inflate(layoutInflater);
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
        refreshBt = ((DeviceActivityPhoneInfoBinding)viewBinding).deviceActivityPhoneInfoRefreshBt;
        screenInfoTv = ((DeviceActivityPhoneInfoBinding)viewBinding).deviceActivityPhoneInfoScreenTv;
        batteryInfoTv = ((DeviceActivityPhoneInfoBinding)viewBinding).deviceActivityPhoneInfoBatteryTv;
        connectivityinfoTv = ((DeviceActivityPhoneInfoBinding)viewBinding).deviceActivityPhoneInfoConnectivityTv;
        appInfoTv = ((DeviceActivityPhoneInfoBinding)viewBinding).deviceActivityPhoneInfoAppTv;

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
        String builder = "Screen:\n" +
                "\nsize:" + ScreenUtils.getScreenSizeInch(this) + " inch" +
                "\ndensity:" + ScreenUtils.getDensity(this) +
                "\nXdpi:" + ScreenUtils.getXdpi(this) + " dpi" +
                "\nYdpi:" + ScreenUtils.getYdpi(this) + " dpi" +
                "\nwidth:" + ScreenUtils.getWidthPX(this) + " px" +
                "\nheight:" + ScreenUtils.getHeightPX(this) + " px" +
                "\nScreenWidth:" + ScreenUtils.getScreenWidthPX(this) + " px" +
                "\nScreenHeight:" + ScreenUtils.getScreenHeightPX(this) + " px" +
                "\nNavigationBarHeight:" + ScreenUtils.getNavigationBarHeight(this) + " px" +
                "\nStatusBarHeight:" + ScreenUtils.getStatusBarHeight(this) + " px";
        screenInfoTv.setText(builder);

        String batteryInfo = "Battery:\n" +
                "\nscale:" + BatteryUtils.getScale(this) +
                "\nlevel:" + BatteryUtils.getLevel(this) +
                "\npercent:" + BatteryUtils.getPercent(this) +
                "\nisCharging:" + BatteryUtils.isCharging(this) +
                "\nChargePlug:" + convertChargePlug2Str(BatteryUtils.getChargePlug(this)) +
                "\nisDocked:" + BatteryUtils.isDocked(this) +
                "\nisCarDock:" + BatteryUtils.isCarDock(this) +
                "\nisDeskDock:" + BatteryUtils.isDeskDock(this);
        batteryInfoTv.setText(batteryInfo);

        String connectivityInfo = "Connectivity\n" +
                "\nisConnected:" + ConnectivityUtils.isConnected(this) +
                "\nActiveNetworkType:" + convertNetType2Str(ConnectivityUtils.getActiveNetworkType(this));
        connectivityinfoTv.setText(connectivityInfo);


        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        StringBuilder appInfo = new StringBuilder();
        appInfo.append("APP\n");
        appInfo.append("\nmemory limit:"+am.getMemoryClass() +"M");

        appInfoTv.setText(appInfo);

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
