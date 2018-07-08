package com.wyq.firehelper.developKit.Dagger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.ArticleResource;
import com.wyq.firehelper.article.WebViewActivity;
import com.wyq.firehelper.developKit.ButterKnife.ButterKnifeActivity;
import com.wyq.firehelper.developKit.DevelopKitBaseActivity;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaggerActivity extends DevelopKitBaseActivity implements View.OnClickListener {

    @BindView(R.id.activity_developkit_dagger_tv)
    public TextView textView;
    @BindView(R.id.activity_developkit_dagger_tv_1)
    public TextView textView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developkit_activity_dagger_layout);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    public void initData() {
        resourceList.put(textView, ArticleConstants._5_1_0);
        resourceList.put(textView1, ArticleConstants._5_1_1);
    }

    @Override
    public void initView() {
        browserArticle(resourceList);
    }

    @Override
    public void browserArticle(Map<View, ArticleResource> resources) {
        if (resources != null && resources.size() > 0) {
            textView.setText(resources.get(textView).getTitle());
            textView1.setText(resources.get(textView1).getTitle());
            textView.setOnClickListener(this);
            textView1.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_developkit_dagger_tv:
                startActivity(ArticleConstants._5_1_0.getUrl());
                break;
            case R.id.activity_developkit_dagger_tv_1:
                startActivity(ArticleConstants._5_1_1.getUrl());
                break;
        }
    }

    public void startActivity(String url) {
        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.setClass(DaggerActivity.this, WebViewActivity.class);
        startActivity(intent);
    }
}
