package com.wyq.firehelper.device;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.article.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.navigation.NavigationManager;
import com.wyq.firehelper.device.PhoneInfo.PhoneInfoActivity;
import com.wyq.firehelper.device.bluetoothChat.BtActivity;

import java.util.List;

/**
 * Created by Uni.W on 2017/10/26.
 */
@Route(path = NavigationManager.NAVIGATION_DEVICE_MAIN_ACTIVITY)
public class DeviceActivity extends BaseRecyclerViewActivity {

    @Override
    public String[] listItemsNames() {
        return new String[]{"PhoneInfo", "Bluetooth"};
    }

    @Override
    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        PhoneInfoActivity.instance(DeviceActivity.this);
                        break;
                    case 1:
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

    @Override
    public String getToolBarTitle() {
        return "Device";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("Device", "设备");
    }
}
