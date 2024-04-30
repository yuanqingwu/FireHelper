package com.wyq.firehelper.ui.android.drawable;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.databinding.UiActivityDrawableBinding;

public class DrawableFragment extends BaseCaseFragment {

    public ImageView imageView;
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
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiActivityDrawableBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        imageView = ((UiActivityDrawableBinding)binding).uiActivityDrawableIv;
        animVDIV = ((UiActivityDrawableBinding)binding).uiActivityDrawableAvdIv;

        imageView.setImageDrawable(new CircularDrawable(Color.CYAN));

        animVDIV.setOnClickListener(v -> {
            isChecked = !isChecked;
            int[] state = {android.R.attr.state_checked * (isChecked ? 1 : -1)};
            animVDIV.setImageState(state, true);
        });
    }
}
