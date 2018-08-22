package com.wyq.firehelper.connectivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.wyq.firehelper.base.BaseListActivity;
import com.wyq.firehelper.connectivity.bluetoothChat.BtActivity;

/**
 * Created by Uni.W on 2017/10/26.
 */

public class ConnectMainActivity extends BaseListActivity {

    @Override
    public String[] listItemsNames() {
        return new String[]{"Bluetooth"};
    }

    @Override
    public String toolBarName() {
        return "Connect";
    }

    @Override
    public boolean isShowBackIcon() {
        return true;
    }

    @Override
    public AdapterView.OnItemClickListener onListItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(ConnectMainActivity.this,
                                BtActivity.class));
                        break;
                    default:
                        break;
                }

            }
        };
    }

}
