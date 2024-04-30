package com.wyq.firehelper.framework.service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.base.BaseCaseActivity;
import com.wyq.firehelper.databinding.FrameworkActivityServiceBinding;

import java.util.List;

public class ServiceActivity extends BaseCaseActivity implements View.OnClickListener {

    public Button button1;

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return FrameworkActivityServiceBinding.inflate(layoutInflater);
    }

    @Override
    public String getToolBarTitle() {
        return "Service";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("Service");
    }

    @Override
    public void initView() {
        button1 = ((FrameworkActivityServiceBinding)viewBinding).serviceActivityMainBt1;
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
        Intent intent = new Intent(ServiceActivity.this, FireService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(ServiceActivity.this, FireService.class));
    }

    public static void instance(Context context) {
        context.startActivity(new Intent(context, ServiceActivity.class));
    }

}
