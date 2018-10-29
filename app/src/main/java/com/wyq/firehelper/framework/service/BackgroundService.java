package com.wyq.firehelper.framework.service;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

public class BackgroundService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BackgroundService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
