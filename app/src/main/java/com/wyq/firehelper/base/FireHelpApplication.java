package com.wyq.firehelper.base;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.Nullable;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.squareup.leakcanary.LeakCanary;
import com.wyq.firehelper.java.aop.aspectj.FireLogTime;

public class FireHelpApplication extends Application {

    /**
     * 当前版本是否为debug版本
     * 1.控制是否打印调试日志
     */
    public static final boolean isDebug = true;

    @FireLogTime(isLog = isDebug)//AOP打印执行时间
    @Override
    public void onCreate() {
        super.onCreate();

        //处理一些一些初始化耗时操作
        AppInitIntentService.start(this);

        if (isDebug) {
            initLeakCanary();
            initLogger();
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
        enabledStrictMode();
        LeakCanary.install(this);
    }

    private void enabledStrictMode(){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
    }

    /**
     * 初始化Logger日志框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .logStrategy(new LogcatLogStrategy()) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("Test")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                //true will print the log message, false will ignore it.
                return FireHelpApplication.isDebug;
            }
        });
    }
}