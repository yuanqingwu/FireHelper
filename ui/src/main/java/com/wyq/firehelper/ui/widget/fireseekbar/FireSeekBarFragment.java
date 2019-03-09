package com.wyq.firehelper.ui.widget.fireseekbar;

import android.view.View;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.R2;

import butterknife.BindView;

/**
 * @author Uni.W
 * @date 2018/11/22 16:01
 */
public class FireSeekBarFragment extends BaseCaseFragment {

    @BindView(R2.id.ui_fragment_fire_progress_bar)
    public FireSeekBar fireSeekBar;

    @Override
    public String[] getArticleFilters() {
        return null;
    }

    @Override
    public String getToolBarTitle() {
        return "FireSeekBar";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_fragment_fire_seek_bar_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }
}
