package com.wyq.firehelper.ui.android.draglayout;

import android.view.View;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

public class DragLayoutFragment extends BaseCaseFragment {
    @Override
    public String[] getArticleFilters() {
        return new String[]{"DragLayout"};
    }

    @Override
    public String getToolBarTitle() {
        return "DragLayout";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_layout_drag_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }
}
