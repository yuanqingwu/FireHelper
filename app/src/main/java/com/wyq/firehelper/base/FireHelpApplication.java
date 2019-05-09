package com.wyq.firehelper.base;

import android.os.StrictMode;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.mmkv.MMKV;
import com.wyq.firehelper.BuildConfig;
import com.wyq.firehelper.base.aop.aspectj.FireLogTime;
import com.wyq.firehelper.developkit.room.AppExecutors;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

public class FireHelpApplication extends MultiDexApplication {

    /**
     * 当前版本是否为debug版本
     * 1.控制是否打印调试日志
     */
    public static final boolean isDebug = BuildConfig.DEBUG;

    private AppExecutors appExecutors;

    @FireLogTime(isLog = true)//AOP打印执行时间
    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();

        //处理一些一些初始化耗时操作
        AppInitIntentService.start(this);

        if (isDebug) {
            initLogger();
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        ARouter.init(this);

        initMMKV();

//        initEmojiCompat();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //todo
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
        //todo
    }

//    public AppDatabase getDatabase() {
//        return AppDatabase.getInstance(this, appExecutors);
//    }
//
//    public DataRepository getRepository() {
//        return DataRepository.getInstance(getDatabase());
//    }

    public void initMMKV() {
        String path = MMKV.initialize(this);
        Logger.i("MMKV PATH:" + path);
    }
//
//    public void initEmojiCompat() {
//        EmojiCompat.Config config = new BundledEmojiCompatConfig(this);
//        EmojiCompat.init(config);
//    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
    }

    /**
     * 初始化Logger日志框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Hides internal method calls up to offset. Default 5
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
