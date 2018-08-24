package com.wyq.firehelper.article;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import co.lujun.androidtagview.ColorFactory;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by Uni.W on 2017/10/26.
 */

public class ArticleMainActivity extends BaseActivity {


    @BindView(R.id.activity_article_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R.id.activity_article_tag_container_layout)
    public TagContainerLayout tagContainerLayout;
    @BindView(R.id.activity_article_main_toolbar)
    public Toolbar toolbar;

    public List<ArticleResource> resourceList;
    public TvRecyclerViewAdapter adapter;
    public SearchView searchView;

    public  List<String> tags;

    /**
     * 记录上次点击tag的位置
     */
//    public int tagClickPos = 0;

    @Override
    protected int attachLayoutRes() {
        return R.layout.article_activity_main;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "Article", true);
    }

    @Override
    public void initView() {
//        initSearchView();
        initTagLayout();
        initRecycleView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //update intent
        setIntent(intent);
//        handleSearch();
    }

//    private void handleSearch() {
//        // Get the intent, verify the action and get the query
//        Intent intent = getIntent();
//        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);
//            doSearch(query);
//        }
//    }

    private void doSearch(String query) {
        toolbar.setTitle(query);
        resourceList = ArticleConstants.getListByFilter(query);
        adapter.refreshData(resourceList);
    }

    @Override
    public boolean onSearchRequested() {
        //activates the search dialog by calling onSearchRequested().
        return super.onSearchRequested();
    }

//    public void initSearchView() {
//
//        titleTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onSearchRequested();
//            }
//        });
//
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//    }

    public void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        resourceList = ArticleConstants.getAllFiled();
        adapter = new TvRecyclerViewAdapter(resourceList);
        adapter.setOnItemClickListener(new TvRecyclerViewAdapter.OnItemClickListener() {
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
        tagContainerLayout.setVisibility(View.GONE);
        tags = ArticleConstants.getAllTags();
        if (tags != null && tags.size() > 0) {
            /**
             * theme 	code 	value 	description
             none 	ColorFactory.NONE 	-1 	If you customize TagView with your way, set this theme
             random 	ColorFactory.RANDOM 	0 	Create each TagView using random color
             pure_cyan 	ColorFactory.PURE_CYAN 	1 	All TagView created by pure cyan color
             pure_teal 	ColorFactory.PURE_TEAL 	2 	All TagView created by pure teal color
             */
            tagContainerLayout.setTheme(ColorFactory.NONE);
            tagContainerLayout.setRippleDuration(100);
            tagContainerLayout.setTagBackgroundColor(Color.WHITE);
            tagContainerLayout.setTags(tags);
            tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
                @Override
                public void onTagClick(int position, String text) {
                    resourceList = ArticleConstants.getListByTag(text);
                    adapter.refreshData(resourceList);

                    tagContainerLayout.setTagBackgroundColor(Color.WHITE);
                    tagContainerLayout.setTags(tags);
                    tagContainerLayout.getTagView(position).setTagBackgroundColor(Color.GRAY);

                    toolbar.setTitle(text);
//                    if (tagClickPos != 0) {
//                        tagContainerLayout.getTagView(tagClickPos).setTagBackgroundColor(Color.WHITE);
//                    }
//                    tagClickPos = position;

                    //选择完之后关闭所有选择界面
                    closeTagContainerLayout();
                    closeSearchView();
                }

                @Override
                public void onTagLongClick(int position, String text) {

                }

                @Override
                public void onTagCrossClick(int position) {
                    //cross间距太大
//                    if (tagContainerLayout.getTagView(position).isEnableCross()) {
//                        tagContainerLayout.getTagView(position).setEnableCross(false);
//                        tagContainerLayout.getTagView(position).setTagBackgroundColor(Color.WHITE);
//                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void closeTagContainerLayout(){
        tagContainerLayout.setTagBackgroundColor(Color.WHITE);
        tagContainerLayout.setTags(tags);
        tagContainerLayout.setVisibility(View.GONE);
    }

    public void closeSearchView(){
        searchView.setQuery("",false);
        searchView.onActionViewCollapsed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("文章搜索");
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagContainerLayout.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                closeTagContainerLayout();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                closeTagContainerLayout();
                closeSearchView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
