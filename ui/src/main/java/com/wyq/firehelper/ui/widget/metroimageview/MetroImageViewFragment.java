package com.wyq.firehelper.ui.widget.metroimageview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.databinding.UiFragmentMetroImageLayoutBinding;

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
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiFragmentMetroImageLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }
}
