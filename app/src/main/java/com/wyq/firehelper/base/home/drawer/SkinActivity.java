package com.wyq.firehelper.base.home.drawer;

import android.content.Context;
import android.content.Intent;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseActivity;

import java.util.List;

public class SkinActivity extends BaseCaseActivity {

    public static void instance(Context context) {
        context.startActivity(new Intent(context, SkinActivity.class));
    }

    @Override
    public String getToolBarTitle() {
        return "Skin";
    }

    @Override
    public List getArticleList() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.drawer_activity_skin;
    }

    @Override
    public void initView() {

    }
}
