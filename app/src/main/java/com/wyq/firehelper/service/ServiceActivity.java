package com.wyq.firehelper.service;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import butterknife.BindView;

public class ServiceActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.service_activity_main_bt1)
    public Button button1;

    @Override
    protected int attachLayoutRes() {
        return R.layout.service_activity_main_layout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        button1.setText("start service");
        button1.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onClick(View v) {
        startService(new Intent(ServiceActivity.this,FireService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(ServiceActivity.this,FireService.class));
    }
}
