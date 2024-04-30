package com.wyq.firehelper.ui.widget.firetoast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.databinding.UiActivityFireToastLayoutBinding;

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
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiActivityFireToastLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    public void initData() {

    }


    @Override
    public void initView(View view) {
        Logger.i("notificationEnabled:"+FireCustomToast.notificationEnabled(getContext()));

        FireToast.instance(getContext(), "1").setText("123456789").setHeadImg(getContext().getResources().getDrawable(R.drawable.ic_vd_face_cyan_24dp)).setTextSize(30)
                .setOnDoubleClickListener(new FireToast.OnDoubleClickListener() {
                    @Override
                    public void onDoubleClick(FireToast fireToast) {
                        fireToast.cancel();
                    }
                }).showCustomToastContinuous(5000,0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                FireToast.instance(getContext(),"2").msg("子线程简单Toast");
            }
        }).start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if( FireToast.get("1") != null){
            FireToast.get("1").cancel();
        }
    }
}
