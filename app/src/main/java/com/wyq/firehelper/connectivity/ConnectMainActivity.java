package com.wyq.firehelper.connectivity;

import android.content.Intent;
import android.view.View;

import com.wyq.firehelper.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.connectivity.bluetoothChat.BtActivity;

/**
 * Created by Uni.W on 2017/10/26.
 */

public class ConnectMainActivity extends BaseRecyclerViewActivity {

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
    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
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
