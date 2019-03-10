package com.wyq.firehelper.article;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.Menu;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.article.entity.ArticleResource;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.base.navigation.NavigationManager;

import java.util.List;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import co.lujun.androidtagview.ColorFactory;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * Created by Uni.W on 2017/10/26.
 */
@Route(path = NavigationManager.NAVIGATION_ARTICLE_MAIN_ACTIVITY)
public class ArticleMainActivity extends BaseActivity implements SearchView.OnCloseListener, View.OnClickListener, SearchView.OnQueryTextListener, View.OnFocusChangeListener {

    @BindView(R2.id.activity_article_recyclerview)
    public RecyclerView recyclerView;
    @BindView(R2.id.activity_article_tag_container_layout)
    public TagContainerLayout tagContainerLayout;
    @BindView(R2.id.activity_article_main_toolbar)
    public Toolbar toolbar;

    public List<ArticleResource> resourceList;
    public TvRecyclerViewAdapter adapter;
    public SearchView searchView;

    public List<String> tags;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        resourceList = ArticleConstants.getAllArticles();
        adapter = new TvRecyclerViewAdapter(resourceList);
        adapter.setOnItemClickListener(new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                WebViewActivity.instance(ArticleMainActivity.this,resourceList.get(position).getUrl());
                ARouter.getInstance().build(NavigationManager.NAVIGATION_ARTICLE_WEBVIEW_ACTIVITY)
                        .withString(NavigationManager.NAVIGATION_KEY_ARTICLE_URL, resourceList.get(position).getUrl())
                        .navigation();
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
//        searchView.cancelPendingInputEvents();
//        searchView = null;
        closeSearchView();
        super.onDestroy();
    }

    public void closeTagContainerLayout() {
        tagContainerLayout.setTagBackgroundColor(Color.WHITE);
        tagContainerLayout.setTags(tags);
        tagContainerLayout.setVisibility(View.GONE);
    }

    public void closeSearchView() {
        searchView.setQuery("", false);
        searchView.onActionViewCollapsed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            searchView.cancelPendingInputEvents();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("文章搜索");

        searchView.setOnSearchClickListener(this);

//        searchView.setOnCloseListener(this);
        searchView.setOnQueryTextFocusChangeListener(this);

        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onClose() {
        closeTagContainerLayout();
        return false;
    }

    @Override
    public void onClick(View v) {
        tagContainerLayout.setVisibility(View.VISIBLE);
    }

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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        closeTagContainerLayout();
    }

    public static void instance(Context context) {
        context.startActivity(new Intent(context, ArticleMainActivity.class));
    }
}
