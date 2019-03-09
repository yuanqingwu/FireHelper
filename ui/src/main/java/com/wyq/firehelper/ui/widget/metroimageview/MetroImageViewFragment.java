package com.wyq.firehelper.ui.widget.metroimageview;

import android.view.View;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;

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
        return R.layout.ui_fragment_metro_image_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }
}
