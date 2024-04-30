package com.wyq.firehelper.article.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyq.firehelper.article.R;
import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.base.BaseFragment;
import com.wyq.firehelper.base.databinding.BaseListFragmentChildLayoutBinding;
import com.wyq.firehelper.base.widget.recyclerview.itemtouchhelper.SimpleItemTouchHelperCallback;

import java.util.Arrays;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseRecyclerViewFragment extends BaseFragment {

    public abstract String[] listItemsNames();

    public abstract TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener();

    public RecyclerView baseRV;

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return BaseListFragmentChildLayoutBinding.inflate(inflater,container,false);
    }

    @CallSuper
    @Override
    public void initView(View view) {
        baseRV = view.findViewById(com.wyq.firehelper.base.R.id.list_fragment_child_recycler_view);
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
        return new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
    }

}
