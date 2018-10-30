package com.wyq.firehelper.framework.thread;

import android.view.View;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;
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
    protected int attachLayoutRes() {
        return R.layout.framework_fragment_thread_handler_thread;
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


