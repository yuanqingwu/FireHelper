package com.wyq.firehelper.base;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wyq.firehelper.R;

import butterknife.BindView;

public abstract class BaseListActivity extends BaseActivity {

    public abstract String[] listItemsNames();
    public abstract String toolBarName();
    public abstract  boolean isShowBackIcon();
    public abstract AdapterView.OnItemClickListener onListItemClickListener();


    @BindView(R.id.home_list)
    public ListView homeListView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    public boolean isShowBackIcon = true;

    @Override
    protected int attachLayoutRes() {
        return R.layout.list_activity_layout;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, toolBarName(), isShowBackIcon());
    }

    @CallSuper
    @Override
    public void initView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                BaseListActivity.this, android.R.layout.simple_list_item_1,
                listItemsNames());
        homeListView.setAdapter(adapter);
        homeListView.setOnItemClickListener(onListItemClickListener());
    }

    public void startActivity(Class activity){
        startActivity(new Intent(BaseListActivity.this,activity));
    }
}
