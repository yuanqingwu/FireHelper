package com.wyq.firehelper.base;

import android.content.Intent;
import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.ui.android.recyclerview.itemtouchhelper.SimpleItemTouchHelperCallback;

import java.util.Arrays;

import butterknife.BindView;

public abstract class BaseRecyclerViewActivity extends BaseCaseActivity {

    public abstract String[] listItemsNames();

    public abstract TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener();

    @BindView(R.id.list_activity_recycler_view)
    public RecyclerView baseRV;

    @Override
    protected int attachLayoutRes() {
        return R.layout.list_activity_layout;
    }

    @CallSuper
    @Override
    public void initView() {
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(Arrays.asList(listItemsNames()));
        if (getItemDecoration() != null) {
            baseRV.addItemDecoration(getItemDecoration());
        }
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
