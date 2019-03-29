package com.wyq.firehelper.framework.service;

import android.os.Build;
import android.service.quicksettings.TileService;

import com.orhanobut.logger.Logger;

import androidx.annotation.RequiresApi;

/**
 * @author Uni.W
 * @date 2019/3/29 20:57
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class FireTileService extends TileService {

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        Logger.i("FireTileService is clicked!");
    }
}
