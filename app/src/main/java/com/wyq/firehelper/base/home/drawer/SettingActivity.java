package com.wyq.firehelper.base.home.drawer;

import android.content.Context;
import android.content.Intent;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.base.BaseCaseActivity;

import java.util.List;

public class SettingActivity extends BaseCaseActivity {

    public static void instance(Context context){
        context.startActivity(new Intent(context,SettingActivity.class));
    }

    @Override
    public String getToolBarTitle() {
        return "Setting";
    }

    @Override
    public List getArticleList() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.drawer_activity_setting;
    }

    @Override
    public void initView() {

    }
}
