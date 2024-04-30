package com.wyq.firehelper.ui.widget.fireseekbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.databinding.UiFragmentFireSeekBarLayoutBinding;

/**
 * @author Uni.W
 * @date 2018/11/22 16:01
 */
public class FireSeekBarFragment extends BaseCaseFragment {

    @Override
    public String[] getArticleFilters() {
        return null;
    }

    @Override
    public String getToolBarTitle() {
        return "FireSeekBar";
    }

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiFragmentFireSeekBarLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }
}
