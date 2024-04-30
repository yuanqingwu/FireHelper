package com.wyq.firehelper.base.home.drawer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.article.base.BaseCaseActivity;
import com.wyq.firehelper.databinding.DrawerActivitySkinBinding;

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
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DrawerActivitySkinBinding.inflate(layoutInflater);
    }

    @Override
    public void initView() {

    }
}
