package com.wyq.firehelper.developkit.androidannotations;

import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.base.BaseCaseActivity;

import java.util.List;

import butterknife.BindView;

public class AndroidAnnotationsActivity extends BaseCaseActivity {

    @BindView(R.id.activity_developkit_androidannotations_tv_1)
    public TextView textView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.developkit_activity_android_annotations_layout;
    }

    @Override
    public void initView() {

    }

    @Override
    public String toolBarName() {
        return "AndroidAnnotations";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("AndroidAnnotations");
    }

}
