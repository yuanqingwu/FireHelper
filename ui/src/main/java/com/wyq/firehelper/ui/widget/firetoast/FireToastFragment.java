package com.wyq.firehelper.ui.widget.firetoast;

import android.view.View;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;

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
        FireToast.instance(getContext(), "1").setText("123456789").setHeadImg(getContext().getResources().getDrawable(R.drawable.ic_vd_face_cyan_24dp)).setTextSize(30)
                .setOnDoubleClickListener(new FireToast.OnDoubleClickListener() {
                    @Override
                    public void onDoubleClick(FireToast fireToast) {
                        fireToast.cancel();
                    }
                }).showCustomToastContinuous(5000,0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if( FireToast.get("1") != null){
            FireToast.get("1").cancel();
        }
    }
}
