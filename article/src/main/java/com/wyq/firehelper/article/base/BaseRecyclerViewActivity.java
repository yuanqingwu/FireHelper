package com.wyq.firehelper.article.base;

import android.content.Intent;
import android.view.LayoutInflater;

import com.wyq.firehelper.article.R;
import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.article.databinding.BaseListActivityLayoutBinding;

import java.util.Arrays;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseRecyclerViewActivity extends BaseCaseActivity {

    public abstract String[] listItemsNames();

    public abstract TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener();

    //    @BindView(R2.id.list_activity_recycler_view)
    public RecyclerView baseRV;

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return BaseListActivityLayoutBinding.inflate(layoutInflater);
    }

    @CallSuper
    @Override
    public void initView() {
        baseRV = findViewById(R.id.list_activity_recycler_view);

        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(Arrays.asList(listItemsNames()));
        if (getItemDecoration() != null) {
            baseRV.addItemDecoration(getItemDecoration());
        }
        if (getLayoutManager() != null) {
            baseRV.setLayoutManager(getLayoutManager());
        }
        baseRV.setAdapter(adapter);
        adapter.setOnItemClickListener(onListItemClickListener());

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(adapter));
//        itemTouchHelper.attachToRecyclerView(baseRV);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
    }

    public void startActivity(Class activity) {
        startActivity(new Intent(BaseRecyclerViewActivity.this, activity));
    }
}
