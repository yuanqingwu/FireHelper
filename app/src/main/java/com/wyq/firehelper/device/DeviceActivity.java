package com.wyq.firehelper.device;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.wyq.firehelper.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.device.bluetoothChat.BtActivity;

/**
 * Created by Uni.W on 2017/10/26.
 */

public class DeviceActivity extends BaseRecyclerViewActivity {

    @Override
    public String[] listItemsNames() {
        return new String[]{"Bluetooth"};
    }

    @Override
    public String toolBarName() {
        return "Device";
    }

    @Override
    public boolean isShowBackIcon() {
        return true;
    }

    @Override
    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(DeviceActivity.this,
                                BtActivity.class));
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public static void instance(Context context) {
        context.startActivity(new Intent(context, DeviceActivity.class));
    }
}
