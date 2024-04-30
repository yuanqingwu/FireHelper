package com.wyq.firehelper.framework.thread;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.databinding.FrameworkFragmentThreadHandlerThreadBinding;
import com.wyq.firehelper.framework.service.BackgroundService;

public class HandlerThreadFragment extends BaseCaseFragment {
    @Override
    public String[] getArticleFilters() {
        return null;
    }

    @Override
    public String getToolBarTitle() {
        return "HandlerThread";
    }

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FrameworkFragmentThreadHandlerThreadBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView(View view) {
       BackgroundService.start(getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}


