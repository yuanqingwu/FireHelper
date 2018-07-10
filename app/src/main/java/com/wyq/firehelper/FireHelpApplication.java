package com.wyq.firehelper;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class FireHelpApplication extends Application {

    /**
     * 当前版本是否为debug版本
     * 1.控制是否打印调试日志
     */
    public static final boolean isDebug = BuildConfig.DEBUG;

    @Override
    public void onCreate() {
        super.onCreate();

        if (isDebug) {
            initLeakCanary();
        }
    }

    /**
     * 初始化内存泄漏检测框架LeakCanary
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

}
