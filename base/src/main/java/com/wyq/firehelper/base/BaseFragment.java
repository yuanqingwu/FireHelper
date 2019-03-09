package com.wyq.firehelper.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutRes(), container, false);
        ButterKnife.bind(this, view);

        initData();
        initView(view);

        return view;
    }

    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 在此填充页面需展示的数据
     */
    protected abstract void initData();

    /**
     * 在此初始化各页面模块
     */
    protected abstract void initView(View view);

}
