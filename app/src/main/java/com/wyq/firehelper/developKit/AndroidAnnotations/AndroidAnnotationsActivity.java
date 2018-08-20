package com.wyq.firehelper.developKit.AndroidAnnotations;

import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.developKit.DevelopKitBaseActivity;

import butterknife.BindView;

public class AndroidAnnotationsActivity extends DevelopKitBaseActivity {

    @BindView(R.id.activity_developkit_androidannotations_tv_1)
    public TextView textView;

    @Override
    public void initData() {
        resourceList.put(textView, ArticleConstants.DEVKIT_INJECT_ANDROIDANNOTATIONS_0);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.developkit_activity_android_annotations_layout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        super.initView();
        browserArticle(AndroidAnnotationsActivity.this);
    }
}
