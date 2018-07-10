package com.wyq.firehelper.developKit.Dagger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.developKit.DevelopKitBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaggerActivity extends DevelopKitBaseActivity {

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
        resourceList.put(textView, ArticleConstants.DEVKIT_INJECT_DAGGER_0);
        resourceList.put(textView1, ArticleConstants.DEVKIT_INJECT_DAGGER_1);
    }

    @Override
    public void initView() {
        browserArticle(DaggerActivity.this);
    }
}
