package com.wyq.firehelper.ui.android.drawable;

import android.graphics.Color;
import android.widget.ImageView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import butterknife.BindView;

public class DrawableActivity extends BaseActivity {

    @BindView(R.id.ui_activity_drawable_iv)
    public ImageView imageView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_drawable;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        imageView.setImageDrawable(new CircularDrawable(Color.CYAN));

    }
}
