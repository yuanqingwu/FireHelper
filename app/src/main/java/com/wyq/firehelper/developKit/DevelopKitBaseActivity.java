package com.wyq.firehelper.developKit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wyq.firehelper.article.entity.ArticleResource;
import com.wyq.firehelper.article.WebViewActivity;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.utils.FireHelperUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DevelopKitBaseActivity extends BaseActivity {

    /**
     * 关联界面与数据
     */
    public Map<TextView, ArticleResource> resourceList = new HashMap<>();

    public abstract void initData();

    @CallSuper
    @Override
    public void initView() {
        resourceList.clear();
        initData();
    }

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

    public List<DevelopKit> getKits(){
        List<DevelopKit> kitsList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONArray kitsArray = new JSONArray(FireHelperUtils.readAssets2String(this,"developKit.json"));
            for(int i=0; i<kitsArray.length();i++){
                DevelopKit kit = gson.fromJson(kitsArray.getString(i),DevelopKit.class);
                kitsList.add(kit);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return kitsList;
    }

}
