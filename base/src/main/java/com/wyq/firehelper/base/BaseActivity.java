package com.wyq.firehelper.base;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

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
        if (toolbar == null) {
            Logger.e("toolbar is null  !!!!");
            return;
        }
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (isShowBackIcon) {
            initToolbarNav(toolbar);
        }
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
