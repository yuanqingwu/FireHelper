package com.wyq.firehelper.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutRes(), container, false);
        ButterKnife.bind(this, view);

        initData();
        initView();

        return view;
    }

    public abstract int attachLayoutRes();

    /**
     * 在此填充页面需展示的数据
     */
    public abstract void initData();

    /**
     * 在此初始化各页面模块
     */
    public abstract void initView();
}
