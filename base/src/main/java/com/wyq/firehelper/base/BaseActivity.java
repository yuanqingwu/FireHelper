package com.wyq.firehelper.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.orhanobut.logger.Logger;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB viewBinding;
    protected abstract VB inflateViewBinding(@NonNull LayoutInflater layoutInflater);

    public abstract void initToolBar();

    public abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = inflateViewBinding(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        initView();
        initToolBar();
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
