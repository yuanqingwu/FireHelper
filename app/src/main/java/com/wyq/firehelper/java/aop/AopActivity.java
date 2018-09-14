package com.wyq.firehelper.java.aop;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.java.aop.aspectj.FireSingleClick;

import butterknife.BindView;

public class AopActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.aop_activity_aop_tv)
    public TextView textView;
    @BindView(R.id.aop_activity_aop_bt)
    public Button button;

    public int index = 0;

    @Override
    protected int attachLayoutRes() {
        return R.layout.aop_activity_aop_layout;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "AOP", true);
    }

    @Override
    public void initView() {
        textView.setText("AOP");
        button.setText("Filtering too fast clicks");
        button.setOnClickListener(new View.OnClickListener() {
            @FireSingleClick
            @Override
            public void onClick(View v) {
                index++;
                textView.setText(""+index);
            }
        });
    }

}
