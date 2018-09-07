package com.wyq.firehelper.ui.layout.recyclerview;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecyclerViewActivity extends BaseActivity implements SelectListener, DetailRVFragment.OnFragmentInteractionListener {

    private DetailRVFragment detailRVFragment;
    private SelectListener selectListener;
    private LinearLayoutManager linearLayoutManager;
    private TvRecyclerViewAdapter adapter;

    @BindView(R.id.ui_activity_recycler_view)
    public RecyclerView recyclerView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_recycler_view_layout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item: " + i);
        }
        adapter = new TvRecyclerViewAdapter(list);
        adapter.setOnItemClickListener(new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelectPosition(position);
                selectListener.select(position, false);
            }
        });
        recyclerView.setAdapter(adapter);

        //初始化右侧界面
        detailRVFragment = DetailRVFragment.newInstance("", "");
        fragmentSelect(detailRVFragment, detailRVFragment.getClass().getSimpleName());
        if (detailRVFragment instanceof SelectListener) {
            this.selectListener = (SelectListener) detailRVFragment;
        }
    }


    private void fragmentSelect(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.ui_activity_recycler_view_frame_layout, fragment, tag);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void select(int position, boolean isScroll) {
        scrollToCenter(position);
        adapter.setSelectPosition(position);
    }

    private void scrollToCenter(int position) {
        View childAt = recyclerView.getChildAt(position - linearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - recyclerView.getHeight() / 2);
            recyclerView.smoothScrollBy(0, y);
        }
    }
}
