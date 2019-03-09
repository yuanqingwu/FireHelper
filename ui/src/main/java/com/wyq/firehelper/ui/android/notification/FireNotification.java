package com.wyq.firehelper.ui.android.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.wyq.firehelper.ui.R;

import androidx.annotation.ColorInt;
import androidx.core.app.NotificationCompat;

/**
 * Author: Uni.W
 * Time: 2018/10/30 16:33
 * Desc: todo
 */
public class FireNotification {
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "FireHelper";
    private static final String NOTIFICATION_CHANNEL_GROUP_ID = "FireHelper";

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;


    private String text  = "";
    private String title = "";
    private int smallIcon;
    private Bitmap largeIcon;
    private boolean showWhen;
    private int progress;
    private PendingIntent contentIntent;


    //******************NotificationChannel************
    private int id;
    private String channelId;
    private String channelGroupId;
    private int importance;//@IntDef(value = {NotificationManager.IMPORTANCE_UNSPECIFIED,NotificationManager.IMPORTANCE_NONE,NotificationManager.IMPORTANCE_MIN,NotificationManager.IMPORTANCE_LOW,NotificationManager.IMPORTANCE_DEFAULT,NotificationManager.IMPORTANCE_HIGH})

    private boolean enableLights;
    @ColorInt
    private int lightColor;



    private void createNotification(Context context,int id,String channelId,String channelGroupId) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(channelGroupId,"FireService"));
            NotificationChannel channel = new NotificationChannel(channelId,"FireService",NotificationManager.IMPORTANCE_DEFAULT);
            channel.setGroup(channelGroupId);
            channel.enableLights(true);
            channel.setLightColor(Color.WHITE);
            channel.setBypassDnd(false);
            channel.setDescription("");
            channel.setLockscreenVisibility(View.VISIBLE);
            channel.setName("FireService");
            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);
            channel.setSound(null,null);
            channel.setVibrationPattern(new long[]{100});
            builder = new NotificationCompat.Builder(context, channelId);
            notificationManager.createNotificationChannel(channel);
        } else {
            builder = new NotificationCompat.Builder(context);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setShowWhen(true);
        builder.setContentText("Fire Service");
        builder.setContentTitle("FireHelper");
        builder.setProgress(100, 0, false);
//        builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, ServiceActivity.class), 0));
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }


    public void cancel(int id){
        if (notificationManager != null) {
            notificationManager.cancel(id);
        }
    }
}
