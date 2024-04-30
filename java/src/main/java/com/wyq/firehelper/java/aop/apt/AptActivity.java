package com.wyq.firehelper.java.aop.apt;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wyq.fireapt.FireApt;
import com.wyq.firehelper.java.R;
import com.wyq.firehelper.java.aop.aspectj.FireLogTime;

import androidx.appcompat.widget.Toolbar;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class AptActivity extends SwipeBackActivity {

    public Toolbar toolbar;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aop_activity_apt_layout);
        FireApt.bind(this);
        toolbar = findViewById(R.id.aop_activity_apt_toolbar);
        textView = findViewById(R.id.aop_activity_apt_tv);
        initToolBar(toolbar, "APT", true);

        textView.setText("Hello APT");
    }


    @FireLogTime
    public void initToolBar(Toolbar toolbar, String title, boolean isShowBackIcon) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (isShowBackIcon) {
            initToolbarNav(toolbar);
        }
    }

    private void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(com.wyq.firehelper.base.R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
