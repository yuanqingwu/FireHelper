package com.wyq.firehelper.developkit.androidannotations;

import android.view.View;
import android.widget.TextView;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.developkit.R;
import com.wyq.firehelper.developkit.R2;

import butterknife.BindView;

public class AndroidAnnotationsFragment extends BaseCaseFragment {

    @BindView(R2.id.activity_developkit_androidannotations_tv_1)
    public TextView textView;

    @Override
    public int attachLayoutRes() {
        return R.layout.developkit_activity_android_annotations_layout;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public String[] getArticleFilters() {
        return new String[]{"AndroidAnnotations"};
    }

    @Override
    public String getToolBarTitle() {
        return "AndroidAnnotations";
    }
}
