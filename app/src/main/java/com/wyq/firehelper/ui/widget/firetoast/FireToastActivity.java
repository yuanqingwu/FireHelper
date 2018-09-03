package com.wyq.firehelper.ui.widget.firetoast;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

public class FireToastActivity extends BaseActivity {

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_fire_toast_layout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        FireToast.showCustomToast(this,"i am toast",FireToast.WARN_YELLOW);
        FireToast.showCustomToastContinuous(this,"i am toast",FireToast.ERROR_RED,5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FireToast.cancelToastContinuous();
    }
}
