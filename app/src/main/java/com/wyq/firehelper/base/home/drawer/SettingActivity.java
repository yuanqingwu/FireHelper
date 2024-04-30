package com.wyq.firehelper.base.home.drawer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.article.base.BaseCaseActivity;
import com.wyq.firehelper.databinding.DrawerActivitySettingBinding;

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
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DrawerActivitySettingBinding.inflate(layoutInflater);
    }

    @Override
    public void initView() {

    }
}
