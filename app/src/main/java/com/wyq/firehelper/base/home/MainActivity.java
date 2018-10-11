package com.wyq.firehelper.base.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;
import com.wyq.firehelper.R;
import com.wyq.firehelper.architecture.ArchitectureActivity;
import com.wyq.firehelper.article.ArticleMainActivity;
import com.wyq.firehelper.article.WebViewActivity;
import com.wyq.firehelper.article.entity.ArticleSaveEntity;
import com.wyq.firehelper.base.adapter.TvImgRecyclerViewAdapter;
import com.wyq.firehelper.developKit.DevelopKitMainActivity;
import com.wyq.firehelper.developKit.mmkv.MMKVManager;
import com.wyq.firehelper.device.DeviceActivity;
import com.wyq.firehelper.encryption.EncryptActivity;
import com.wyq.firehelper.kotlin.mvpGitHub.view.GitHubMainActivity;
import com.wyq.firehelper.media.opengles.OpenGLESActivity;
import com.wyq.firehelper.service.ServiceActivity;
import com.wyq.firehelper.ui.UiMainActivity;
import com.wyq.firehelper.ui.android.popupwindow.FirePopupWindow;
import com.wyq.firehelper.ui.android.recyclerview.itemtouchhelper.SimpleItemTouchHelperCallback;
import com.wyq.firehelper.utils.FireHelperUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class MainActivity extends AppCompatActivity implements TvImgRecyclerViewAdapter.OnTvImgItemClickListener, TvImgRecyclerViewAdapter.OnTvImgItemLongClickListener {

    @BindView(R.id.activity_main_rv)
    public RecyclerView baseRV;
    @BindView(R.id.ui_activity_pull_ext_view_rv)
    public RecyclerView exRecyclerView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.main_activity_article_cardview)
    public CardView articleView;

    private MMKV articleMMKV = MMKV.mmkvWithID(MMKVManager.MMKV_ID_ARTICLE, MMKV.MULTI_PROCESS_MODE);
    private List<ArticleSaveEntity> articleSaveEntities;
    private TvImgRecyclerViewAdapter extAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        ButterKnife.bind(this);

        initToolBar(toolbar, getString(R.string.app_name));
        initData();
        initView();
    }

    public void initToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    public void initData() {
        articleSaveEntities = new ArrayList<>();
    }

    public void initView() {
        AppCompatDelegate.setDefaultNightMode(NightThemeConfig.getInstance(this).getNightMode());

        initRecyclerView();

        articleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleMainActivity.instance(getApplicationContext());
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        //刷新头部数据
        if (extAdapter != null)
            extAdapter.refreshData(refreshSavedArticles());
    }

    /**
     * 删除某篇文章
     * @param position
     */
    public List<ArticleSaveEntity> deleteSavedArticle(int position){
        if(articleSaveEntities != null && articleSaveEntities.size()>=position){
            ArticleSaveEntity entity = articleSaveEntities.get(position);
            articleMMKV.removeValueForKey(entity.getResource().getUrl());
            articleSaveEntities.remove(position);
        }
        return articleSaveEntities;
    }

    /**
     * 刷新文章列表
     * @return
     */
    public List<ArticleSaveEntity> refreshSavedArticles() {
        String[] articles = articleMMKV.allKeys();
        articleSaveEntities.clear();
        if (articles != null && articles.length > 0)
            for (String key : articles) {
                ArticleSaveEntity entity = ArticleSaveEntity.convertFromBytes(articleMMKV.decodeBytes(key));
                articleSaveEntities.add(entity);
                Logger.i("mmkv:" + entity.toString());
            }

        return articleSaveEntities;
    }

    public void initRecyclerView() {

        List<FireModule> fireModules = getModuleList();
        fireModules.remove(0);//文章模块单独处理
        TvImgRecyclerViewAdapter adapter = new TvImgRecyclerViewAdapter(this, fireModules);
        adapter.setOrientation(TvImgRecyclerViewAdapter.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        //将文章card单独一行
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return fireModules.get(position).getTitleEn().equals("Article")?2:1;
//            }
//        });

        baseRV.setLayoutManager(gridLayoutManager);
        baseRV.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        //首页各模块可以移动交换位置
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(baseRV);

        extAdapter = new TvImgRecyclerViewAdapter(this, refreshSavedArticles());
        extAdapter.setOnItemClickListener(this);
        extAdapter.setOnItemLongClickListener(this);
        extAdapter.setOrientation(TvImgRecyclerViewAdapter.HORIZONTAL);
        exRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        exRecyclerView.setAdapter(extAdapter);
    }

    public List<FireModule> getModuleList() {
        String json = FireHelperUtils.readAssets2String(this, "module.json");
//        Logger.i(json);
        try {
            JSONArray jsonArray = new JSONArray(json);
            List<FireModule> fireModules = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                FireModule module = FireModule.convertFromJson(jsonArray.getString(i));
                fireModules.add(module);
            }
            return fireModules;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        switch (AppCompatDelegate.getDefaultNightMode()) {
            case AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM:
                menu.findItem(R.id.menu_night_mode_system).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_AUTO:
                menu.findItem(R.id.menu_night_mode_auto).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_YES:
                menu.findItem(R.id.menu_night_mode_night).setChecked(true);
                break;
            case AppCompatDelegate.MODE_NIGHT_NO:
                menu.findItem(R.id.menu_night_mode_day).setChecked(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_night_mode_system:
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case R.id.menu_night_mode_day:
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.menu_night_mode_night:
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case R.id.menu_night_mode_auto:
                setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * AppCompatDelegate.setDefaultNightMode(int mode);只作用于新生成的组件，对原先处于任务栈中的Activity不起作用。如果直接在Activity的onCreate()中调用这句代码，那当前的Activity中直接生效，不需要再调用recreate(),但我们通常是在监听器中调用这句代码，那就需要调用recreate()，否则对当前Activity无效(新生成的Activity还是生效的)。当然可以提前在onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)中保存好相关属性值,重建时使用。
     *
     * @param nightMode
     */
    private void setNightMode(@AppCompatDelegate.NightMode int nightMode) {
        AppCompatDelegate.setDefaultNightMode(nightMode);
        NightThemeConfig.getInstance(this).setNightMode(nightMode);
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.recyclerview_item_tv_img_layout_h:
                WebViewActivity.instance(MainActivity.this, articleSaveEntities.get(position).getResource().getUrl());
                break;
            case R.id.recyclerview_item_tv_img_layout_v:
                switch (position) {
                    case 0:
                        DeviceActivity.instance(getApplicationContext());
                        break;
                    case 1:
                        UiMainActivity.instance(getApplicationContext());
                        break;
                    case 2:
                        EncryptActivity.instance(getApplicationContext());
                        break;
                    case 3:
                        DevelopKitMainActivity.instance(getApplicationContext());
                        break;
                    case 4:
                        ArchitectureActivity.instance(getApplicationContext());
                        break;
                    case 5:
                        ServiceActivity.instance(getApplicationContext());
                        break;
                    case 6:
                        OpenGLESActivity.instance(MainActivity.this);
                        break;
                    case 7:
                        startActivity(new Intent(MainActivity.this,
                                GitHubMainActivity.class));
                        break;

                    default:
                        break;

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        switch (view.getId()) {
            case R.id.recyclerview_item_tv_img_layout_h:
                FirePopupWindow.text("删除").showAsDropDown(view).setOnClickListener(new FirePopupWindow.OnClickListener() {
                    @Override
                    public void onClick(String text) {
                        if(text.equals("删除")){
                            extAdapter.refreshData(deleteSavedArticle(position));
                        }
                    }
                });

                break;
            default:
                break;
        }
    }
}
