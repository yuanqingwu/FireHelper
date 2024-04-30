package com.wyq.firehelper.framework.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.Logger;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * author: Uni.W
 * time: 2018/10/30 15:23
 *
 * describe:
 */
public class BackgroundService extends BackTaskService {

    private static final String ACTION = "BackgroundService";
    public BackgroundService() {

    }

    public static void start(Context context){
        Intent intent = new Intent();
        intent.setAction(ACTION);
        context.startService(new Intent(context,BackgroundService.class));
    }

    @Override
    protected void execute(Intent intent) {
        int r = 0;
        for(int i=0;i<1000;i++){
            r+=i;
        }
        intent.putExtra(ACTION,r);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        Logger.i("back task 1+2+...+1000 = "+r);
    }

    public abstract class BackTaskService extends Service {
        private ThreadHandler threadHandler;
        private volatile Looper handlerLooper;
        private String name;

        public BackTaskService(){

        }

        public BackTaskService(String name){
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

            HandlerThread thread = new HandlerThread("handlerThread["+name+"]");
            thread.start();
            Logger.i("onCreate"+thread.getThreadId());
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
}
