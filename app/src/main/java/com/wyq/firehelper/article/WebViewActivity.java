package com.wyq.firehelper.article;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.developKit.eventBus.EventBusMessage;
import com.wyq.firehelper.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_layout);

        String url = getIntent().getStringExtra("url");

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        transaction.replace(R.id.activity_webview_framelayout, fragment);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
