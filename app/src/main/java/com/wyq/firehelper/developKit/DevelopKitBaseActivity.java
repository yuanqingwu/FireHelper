package com.wyq.firehelper.developKit;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wyq.firehelper.article.ArticleResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DevelopKitBaseActivity extends AppCompatActivity{

    public Map<View,ArticleResource> resourceList = new HashMap<>();

    public abstract void initData();

    public abstract void initView();

    public abstract void browserArticle(Map<View,ArticleResource> resources);


}
