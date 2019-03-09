package com.wyq.firehelper.ui.android.drawable;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.R2;

import butterknife.BindView;
import butterknife.OnClick;

public class DrawableFragment extends BaseCaseFragment {

    @BindView(R2.id.ui_activity_drawable_iv)
    public ImageView imageView;
    @BindView(R2.id.ui_activity_drawable_vector_iv)
    public ImageView vectorIV;
    @BindView(R2.id.ui_activity_drawable_avd_iv)
    public ImageView animVDIV;

    private boolean isChecked = false;

    @Override
    public String[] getArticleFilters() {
        return new String[]{"Drawable","Vector"};
    }

    @Override
    public String getToolBarTitle() {
        return "Drawable";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_drawable;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        imageView.setImageDrawable(new CircularDrawable(Color.CYAN));
    }

    @OnClick(R2.id.ui_activity_drawable_avd_iv)
    public void click() {
        isChecked = !isChecked;
        int[] state = {android.R.attr.state_checked * (isChecked ? 1 : -1)};
        animVDIV.setImageState(state, true);
    }
}
