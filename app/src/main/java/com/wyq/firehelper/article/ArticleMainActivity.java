package com.wyq.firehelper.article;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by Uni.W on 2017/10/26.
 */

public class ArticleMainActivity extends BaseActivity {

    @BindView(R.id.activity_article_title_tv)
    public TextView titleTv;
    @BindView(R.id.activity_article_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.activity_article_tag_container_layout)
    public TagContainerLayout tagContainerLayout;
    @BindView(R.id.activity_article_search_view)
    public SearchView searchView;

    public List<ArticleResource> resourceList;
    public RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_activity_main);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        initSearchView();
        initTagLayout();
        initRecycleView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //update intent
        setIntent(intent);
        handleSearch();
    }

    private void handleSearch() {
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        resourceList = ArticleConstants.getListByFilter(query);
        adapter.refreshData(resourceList);
    }

    @Override
    public boolean onSearchRequested() {
        //activates the search dialog by calling onSearchRequested().
        return super.onSearchRequested();
    }

    public void initSearchView() {

        titleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchRequested();
            }
        });

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Logger.i("query:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Logger.i("newText:" + newText);
                return false;
            }
        });
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {

                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {

                return false;
            }
        });
    }

    public void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        resourceList = ArticleConstants.getAllFiled();
        adapter = new RecyclerViewAdapter(resourceList);
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("url", resourceList.get(position).getUrl());
                intent.setClass(ArticleMainActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void initTagLayout() {
        List<String> tags = ArticleConstants.getAllTags();
        if (tags != null && tags.size() > 0) {
            /**
             * theme 	code 	value 	description
             none 	ColorFactory.NONE 	-1 	If you customize TagView with your way, set this theme
             random 	ColorFactory.RANDOM 	0 	Create each TagView using random color
             pure_cyan 	ColorFactory.PURE_CYAN 	1 	All TagView created by pure cyan color
             pure_teal 	ColorFactory.PURE_TEAL 	2 	All TagView created by pure teal color
             */
            tagContainerLayout.setTheme(1);
            tagContainerLayout.setRippleDuration(800);
            tagContainerLayout.setTags(tags);
            tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
                @Override
                public void onTagClick(int position, String text) {
                    resourceList = ArticleConstants.getListByTag(text);
                    adapter.refreshData(resourceList);
                }

                @Override
                public void onTagLongClick(int position, String text) {

                }

                @Override
                public void onTagCrossClick(int position) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        return super.onCreateOptionsMenu(menu);
    }
}
