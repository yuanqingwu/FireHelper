package com.wyq.firehelper.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wyq.firehelper.R;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public abstract class BaseActivity extends SwipeBackActivity {

    /**
     * 绑定布局文件
     *
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    public abstract void initToolBar();
    public abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(attachLayoutRes());
        ButterKnife.bind(this);

        initToolBar();
        initView();
    }

    public void initToolBar(Toolbar toolbar, String title, boolean isShowBackIcon) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (isShowBackIcon) initToolbarNav(toolbar);
    }

    private void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
