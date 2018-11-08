package com.wyq.firehelper.base.home.drawer;

import android.content.Context;
import android.content.Intent;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseActivity;
import com.wyq.firehelper.component.share.FireShare;

import java.util.List;

import butterknife.OnClick;

public class ShareActivity extends BaseCaseActivity {


    public static void instance(Context context) {
        context.startActivity(new Intent(context, ShareActivity.class));
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.drawer_activity_share;
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

    }

    @OnClick(R.id.drawer_activity_share_bt)
    public void onClick() {
        FireShare.shareTextWithSys(ShareActivity.this, "https://github.com/wuyuanqing527/FireHelper", "share");
    }
}
