package com.wyq.firehelper.developKit;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.activity.BaseActivity;
import com.wyq.firehelper.article.ArticleResource;
import com.wyq.firehelper.article.WebViewActivity;

import java.util.HashMap;
import java.util.Map;

public abstract class DevelopKitBaseActivity extends BaseActivity {

    /**
     * 关联界面与数据
     */
    public Map<TextView, ArticleResource> resourceList = new HashMap<>();

    public DevelopKitBaseActivity() {
        resourceList.clear();
//        Logger.i("resourceList.clear()");
    }

    /**
     * 在此填充页面需展示的数据
     */
    public abstract void initData();

    /**
     * 在此初始化各页面模块
     */
    public abstract void initView();

    /**
     * 构建文章阅读板块
     *
     * @param context
     */
    public void browserArticle(final Context context) {
        if (resourceList != null && resourceList.size() > 0) {
            for (TextView v : resourceList.keySet()) {
                v.setText(resourceList.get(v).getTitle());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(context, resourceList.get(v).getUrl());
                    }
                });
            }
        }

    }

    /**
     * 跳转浏览器界面
     *
     * @param context
     * @param url
     */
    public void startActivity(Context context, String url) {
        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.setClass(context, WebViewActivity.class);
        startActivity(intent);
    }

}
