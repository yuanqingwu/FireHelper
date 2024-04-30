package com.wyq.firehelper.java.aop;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.java.R;
import com.wyq.firehelper.java.aop.aspectj.FireSingleClick;
import com.wyq.firehelper.java.databinding.AopActivityAopLayoutBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

public class AopActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView textView;
    public Button button;

    public int index = 0;

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return AopActivityAopLayoutBinding.inflate(layoutInflater);
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "AOP", true);
    }

    @Override
    public void initView() {
        toolbar = ((AopActivityAopLayoutBinding)viewBinding).aopActivityAopToolbar.toolbar;
        textView = ((AopActivityAopLayoutBinding)viewBinding).aopActivityAopTv;
        button = ((AopActivityAopLayoutBinding)viewBinding).aopActivityAopBt;

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
