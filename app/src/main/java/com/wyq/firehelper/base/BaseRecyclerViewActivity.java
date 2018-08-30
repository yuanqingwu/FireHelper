package com.wyq.firehelper.base;

import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.ui.layout.recyclerView.SimpleItemTouchHelperCallback;

import java.util.Arrays;

import butterknife.BindView;

public abstract class BaseRecyclerViewActivity extends BaseActivity {

    public abstract String[] listItemsNames();

    public abstract String toolBarName();

    public abstract boolean isShowBackIcon();

    public abstract TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener();

    @BindView(R.id.list_activity_recycler_view)
    public RecyclerView baseRV;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

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
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(Arrays.asList(listItemsNames()));
//        if (getItemDecoration() != null) {
//            baseRV.addItemDecoration(getItemDecoration());
//        }
        if (getLayoutManager() != null) {
            baseRV.setLayoutManager(getLayoutManager());
        }
        baseRV.setAdapter(adapter);
        adapter.setOnItemClickListener(onListItemClickListener());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter));
        itemTouchHelper.attachToRecyclerView(baseRV);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
    }

    public void startActivity(Class activity) {
        startActivity(new Intent(BaseRecyclerViewActivity.this, activity));
    }
}
