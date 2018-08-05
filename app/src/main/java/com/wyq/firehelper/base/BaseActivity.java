package com.wyq.firehelper.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wyq.firehelper.R;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class BaseActivity extends SwipeBackActivity {


    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
