package com.wyq.firehelper.ui.widget.metroimageview;

import android.view.View;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

public class MetroImageViewFragment extends BaseCaseFragment {

    @Override
    public String[] getArticleFilters() {
        return new String[]{"自定义View"};
    }

    @Override
    public String getToolBarTitle() {
        return "MetroImageView";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.metrolayout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }
}
