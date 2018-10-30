package com.wyq.firehelper.framework.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import butterknife.BindView;

/**
 * author: Uni.W
 * time: 2018/10/30 16:11
 *
 * describe:
 */
public class LocalBroadcastFragment extends BaseCaseFragment {

    @BindView(R.id.framework_activity_local_broadcast_res_tv)
    public TextView textView;

    private LBReceiver receiver;
    private String ACTION = "LocalBroadcast";

    @Override
    public String[] getArticleFilters() {
        return null;
    }

    @Override
    public String getToolBarTitle() {
        return "LocalBroadcast";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.framework_fragment_local_broadcast;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        receiver = new LBReceiver();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter(ACTION));

        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.putExtra("name", "i am from LocalBroadcast");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
    }

    public class LBReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String res = intent.getStringExtra("name");
            textView.setText(res);
            Logger.i(res);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }
}
