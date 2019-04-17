package com.wyq.firehelper.base.utils.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Uni.W
 * @date 2018/12/12 11:17
 */
public class ScreenUtils {

    @RequiresApi(Build.VERSION_CODES.P)
    public static boolean isNotchScreen(AppCompatActivity activity) {
        boolean isNotchScreen = false;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            View decorView = activity.getWindow().getDecorView();
            WindowInsets windowInsets = decorView.getRootWindowInsets();
            if (windowInsets != null) {
                    DisplayCutout displayCutout = windowInsets.getDisplayCutout();
                if (displayCutout != null) {
                    List<Rect> rects = displayCutout.getBoundingRects();
                    //通过判断是否存在rects来确定是否刘海屏手机
                    if (rects != null && rects.size() > 0) {
                        isNotchScreen = true;
                    }
                }
            }
//        }
        return isNotchScreen;
    }

    /**
     * 得到屏幕真实宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidthPX(AppCompatActivity context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(dm);
        } else {
            display.getMetrics(dm);
        }
        int realHeight = dm.widthPixels;
        return realHeight;
    }

    /**
     * 得到屏幕真实高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeightPX(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(dm);
        } else {
            display.getMetrics(dm);
        }
        int realHeight = dm.heightPixels;
        return realHeight;
    }

    public static int getWidthPX(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * 获取屏幕高度（不包含导航栏高度）
     *
     * @param context
     * @return
     */
    public static int getHeightPX(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.heightPixels;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;

    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * The exact physical pixels per inch of the screen in the X dimension.
     * @param context
     * @return
     */
    public static float getXdpi(Context context){
        return context.getResources().getDisplayMetrics().xdpi;
    }

    /**
     * The exact physical pixels per inch of the screen in the Y dimension.
     * @param context
     * @return
     */
    public static float getYdpi(Context context){
        return context.getResources().getDisplayMetrics().ydpi;
    }

    /**
     * get screen size (unit inch)
     * @param context
     * @return
     */
    public static int getScreenSizeInch(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        double x = Math.pow(metrics.widthPixels/ metrics.xdpi, 2);
        double y = Math.pow(metrics.heightPixels / metrics.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return ((int) screenInches);
    }

}
