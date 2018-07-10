package com.wyq.firehelper.developKit.AndroidAnnotations;

import android.os.Bundle;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.developKit.DevelopKitBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AndroidAnnotationsActivity extends DevelopKitBaseActivity {

    @BindView(R.id.activity_developkit_androidannotations_tv_1)
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developkit_activity_android_annotations_layout);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    @Override
    public void initData() {
        resourceList.put(textView, ArticleConstants.DEVKIT_INJECT_ANDROIDANNOTATIONS_0);
    }

    @Override
    public void initView() {
        browserArticle(AndroidAnnotationsActivity.this);
    }
}
