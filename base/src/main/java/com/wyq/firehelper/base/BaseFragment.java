package com.wyq.firehelper.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    protected VB binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = getViewBinding(inflater,container);
        initData();
        initView(binding.getRoot());
        return binding.getRoot();
    }

    protected abstract VB getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 在此填充页面需展示的数据
     */
    protected abstract void initData();

    /**
     * 在此初始化各页面模块
     */
    protected abstract void initView(View view);

}
