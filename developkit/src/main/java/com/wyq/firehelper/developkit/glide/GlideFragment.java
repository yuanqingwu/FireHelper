package com.wyq.firehelper.developkit.glide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.developkit.databinding.DevelopkitActivityGlideLayoutBinding;

public class GlideFragment extends BaseCaseFragment {

    public ImageView imageView;

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return DevelopkitActivityGlideLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView(View view) {
        imageView = ((DevelopkitActivityGlideLayoutBinding)binding).activityDevelopkitGlideImg1;
        Glide.with(getActivity()).load("https://github.com/bumptech/glide/raw/master/static/glide_logo.png")
                .into(imageView);
    }

    @Override
    public String[] getArticleFilters() {
        return new String[]{"Glide"};
    }

    @Override
    public String getToolBarTitle() {
        return "Glide";
    }
}
