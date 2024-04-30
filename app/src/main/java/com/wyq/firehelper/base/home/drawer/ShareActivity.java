package com.wyq.firehelper.base.home.drawer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.base.BaseCaseActivity;
import com.wyq.firehelper.component.share.FireShare;
import com.wyq.firehelper.databinding.DrawerActivityShareBinding;

import java.util.List;

public class ShareActivity extends BaseCaseActivity {


    public static void instance(Context context) {
        context.startActivity(new Intent(context, ShareActivity.class));
    }

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DrawerActivityShareBinding.inflate(layoutInflater);
    }

    @Override
    public String getToolBarTitle() {
        return "Share";
    }

    @Override
    public List getArticleList() {
        return null;
    }

    @Override
    public void initView() {

        ((DrawerActivityShareBinding)viewBinding).drawerActivityShareBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FireShare.shareTextWithSys(ShareActivity.this, "https://github.com/wuyuanqing527/FireHelper", "share");

            }
        });
    }
}
