package com.wyq.firehelper.framework.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;

public class FireService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "FireService";
    private static final String NOTIFICATION_CHANNEL_GROUP_ID = "FireService";

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Thread thread;

    private volatile boolean isStop;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("onCreate");

        if (thread == null)
            thread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            int i = 0;
                            for (; ; ) {
                                Logger.i("service");
                                if (isStop || i == 100) {
                                    stopSelf();
                                    break;
                                }
                                if (builder != null) {
                                    i = i + 5;
                                    builder.setProgress(100, i, false);
                                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d("onStartCommand");
        createNotification();
        if (thread != null && !thread.isAlive())
            thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("onBind");
        return null;
    }

    @Override
    public void onTrimMemory(int level) {
        Logger.d("onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        Logger.d("onLowMemory");
        super.onLowMemory();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Logger.d("onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        Logger.d("onDestroy");
        super.onDestroy();
        isStop = true;
        notificationManager.cancel(NOTIFICATION_ID);
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
    }

    private void createNotification() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(NOTIFICATION_CHANNEL_GROUP_ID,"FireService"));
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"FireService",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setGroup(NOTIFICATION_CHANNEL_GROUP_ID);
            channel.enableLights(true);
            channel.setLightColor(Color.WHITE);
            builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(channel);
        } else {
            builder = new NotificationCompat.Builder(this);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setShowWhen(true);
        builder.setContentText("Fire Service");
        builder.setContentTitle("FireHelper");
        builder.setProgress(100, 0, false);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, ServiceActivity.class), 0));
        Notification notification = builder.build();
//        notificationManager.notify(NOTIFICATION_ID, notification);
        startForeground(NOTIFICATION_ID, notification);
    }

}
