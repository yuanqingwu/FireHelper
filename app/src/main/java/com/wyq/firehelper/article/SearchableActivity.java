package com.wyq.firehelper.article;

import android.app.SearchManager;
import android.content.Intent;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import butterknife.BindView;

public class SearchableActivity extends BaseActivity {

    @BindView(R.id.article_activity_search_tv)
    public TextView textView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.article_activity_search;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        Logger.i(query);
        textView.setText(query);
    }
}
