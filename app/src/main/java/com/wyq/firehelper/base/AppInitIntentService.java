package com.wyq.firehelper.base;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class AppInitIntentService extends IntentService {

    public static String APP_INIT_ACTION = "APP_INIT_ACTION";

    public AppInitIntentService() {
        super("AppInitIntentService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AppInitIntentService.class);
        intent.setAction(APP_INIT_ACTION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (APP_INIT_ACTION.equals(intent.getAction())) {
            doInitTask();
        }
    }


    private void doInitTask() {
        //todo
        Log.i("Test","AppInitIntentService is working");
    }
}
