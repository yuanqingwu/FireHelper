package com.wyq.firehelper.ui.widget.firetoast;

import android.view.View;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

public class FireToastFragment extends BaseCaseFragment {
    @Override
    public String[] getArticleFilters() {
        return new String[]{"Toast"};
    }

    @Override
    public String getToolBarTitle() {
        return "FireToast";
    }

    @Override
    public int attachLayoutRes() {
        return R.layout.ui_activity_fire_toast_layout;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(View view) {
        FireToast.showCustomToast(getContext(), "i am toast", FireToast.WARN_YELLOW);
        FireToast.showCustomToastContinuous(getContext(), "i am toast", FireToast.ERROR_RED, 5000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FireToast.cancelToastContinuous();
    }
}
