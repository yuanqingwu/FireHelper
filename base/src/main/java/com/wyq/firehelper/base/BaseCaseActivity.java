package com.wyq.firehelper.base;

import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wyq.firehelper.base.article.entity.ArticleResource;
import com.wyq.firehelper.base.navigation.NavigationManager;
import com.wyq.firehelper.base.widget.FirePopupWindow;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public abstract class BaseCaseActivity extends BaseActivity {

    @BindView(R2.id.toolbar_article)
    public Toolbar toolbar;
    @BindView(R2.id.toolbar_article_iv)
    public ImageView articleIv;

    @Override
    public void initToolBar() {
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
                                        .withString("url", ((ArticleResource) data).getUrl())
                                        .navigation();
                            }
                        }
                    });
                }
            }
        });
    }

    public void refreshToolBarTitle() {
        toolbar.setTitle(getToolBarTitle());
    }

    public void refreshArticleList() {
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
