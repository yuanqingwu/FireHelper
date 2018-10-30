package com.wyq.firehelper.framework.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

/**
 * author: Uni.W
 * time: 2018/10/30 16:26
 *
 * describe:
 */
public abstract class BackTaskService extends Service {
    private ThreadHandler threadHandler;
    private volatile Looper handlerLooper;
    private String name;

    public BackTaskService() {

    }

    public BackTaskService(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread thread = new HandlerThread("handlerThread[" + name + "]");
        thread.start();//不能写成run()!
        Logger.i("onCreate" + thread.getThreadId());
        handlerLooper = thread.getLooper();
        threadHandler = new ThreadHandler(handlerLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = threadHandler.obtainMessage();
        msg.arg1 = startId;
        msg.obj = intent;
        threadHandler.sendMessage(msg);
        Logger.i("onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerLooper.quit();
    }

    @WorkerThread
    protected abstract void execute(Intent intent);

    private final class ThreadHandler extends Handler {

        public ThreadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //do something
            execute((Intent) msg.obj);
            stopSelf(msg.arg1);
        }
    }
}
