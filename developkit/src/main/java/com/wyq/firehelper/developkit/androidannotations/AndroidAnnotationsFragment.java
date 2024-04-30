package com.wyq.firehelper.developkit.androidannotations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.developkit.databinding.DevelopkitActivityAndroidAnnotationsLayoutBinding;

public class AndroidAnnotationsFragment extends BaseCaseFragment {

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return DevelopkitActivityAndroidAnnotationsLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public String[] getArticleFilters() {
        return new String[]{"AndroidAnnotations"};
    }

    @Override
    public String getToolBarTitle() {
        return "AndroidAnnotations";
    }
}
