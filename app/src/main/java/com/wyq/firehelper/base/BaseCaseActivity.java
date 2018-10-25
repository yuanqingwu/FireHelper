package com.wyq.firehelper.base;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.WebViewActivity;
import com.wyq.firehelper.article.entity.ArticleResource;
import com.wyq.firehelper.ui.android.popupwindow.FirePopupWindow;

import java.util.List;

import butterknife.BindView;

public abstract class BaseCaseActivity extends BaseActivity {

    @BindView(R.id.toolbar_article)
    public Toolbar toolbar;
    @BindView(R.id.toolbar_article_iv)
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
                                WebViewActivity.instance(BaseCaseActivity.this, ((ArticleResource) data).getUrl());
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
