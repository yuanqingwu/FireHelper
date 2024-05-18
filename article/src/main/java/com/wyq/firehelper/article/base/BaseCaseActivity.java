package com.wyq.firehelper.article.base;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wyq.firehelper.article.R;
import com.wyq.firehelper.article.entity.ArticleResource;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.base.navigation.NavigationManager;

import java.util.List;

import androidx.appcompat.widget.Toolbar;

public abstract class BaseCaseActivity extends BaseActivity {

    public Toolbar toolbar;
    public ImageView articleIv;

    @Override
    public void initToolBar() {
        toolbar = findViewById(R.id.toolbar_article);
        if(toolbar == null){
            return;
        }
        articleIv = toolbar.findViewById(R.id.toolbar_article_iv);

        initToolBar(toolbar, getToolBarTitle(), true);
        refreshArticleList();
        articleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List articleList = getArticleList();
                if (articleList != null && articleList.size() > 0) {
                    FirePopupWindow.list(getArticleList()).showLocation(getWindow().getDecorView()).setOnItemClickListener(new FirePopupWindow.OnItemClickListener() {
                        @Override
                        public void onItemClick(Object data) {
                            if (data instanceof ArticleResource) {
//                                WebViewActivity.instance(BaseCaseActivity.this, ((ArticleResource) data).getUrl());
                                ARouter.getInstance().build(NavigationManager.NAVIGATION_ARTICLE_WEBVIEW_ACTIVITY)
                                        .withString(NavigationManager.NAVIGATION_KEY_ARTICLE_URL, ((ArticleResource) data).getUrl())
                                        .navigation();
                            }
                        }
                    });
                }
            }
        });
    }

    public void refreshToolBarTitle() {
        if(toolbar!= null) {
            toolbar.setTitle(getToolBarTitle());
        }
    }

    public void refreshArticleList() {
        if(articleIv == null){
            return;
        }
        List articleList = getArticleList();
        if (articleList == null || articleList.size() == 0) {
            articleIv.setVisibility(View.GONE);
        } else {
            articleIv.setVisibility(View.VISIBLE);
        }
    }

    public abstract String getToolBarTitle();

    public abstract List getArticleList();
}
