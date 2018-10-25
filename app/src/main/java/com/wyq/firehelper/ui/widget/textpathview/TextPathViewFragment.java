package com.wyq.firehelper.ui.widget.textpathview;

import android.view.View;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

import butterknife.BindView;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.FireworksPainter;

public class TextPathViewFragment extends BaseCaseFragment {

    @BindView(R.id.ui_activity_text_path_view_sync)
    public SyncTextPathView syncTextPathView;

    @Override
    public int attachLayoutRes() {
        return R.layout.ui_activity_text_path_view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        syncTextPathView.setPathPainter(new FireworksPainter());
        syncTextPathView.startAnimation(0, 1);
    }

    @Override
    public String[] getArticleFilters() {
        return new String[]{"TextPathView"};
    }

    @Override
    public String getToolBarTitle() {
        return "TextPathView";
    }
}
