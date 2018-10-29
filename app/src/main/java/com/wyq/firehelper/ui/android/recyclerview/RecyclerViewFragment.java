package com.wyq.firehelper.ui.android.recyclerview;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecyclerViewFragment extends BaseCaseFragment implements SelectListener {

    private DetailRVFragment detailRVFragment;
    private SelectListener selectListener;
    private LinearLayoutManager linearLayoutManager;
    private TvRecyclerViewAdapter adapter;

    @BindView(R.id.ui_activity_recycler_view)
    public RecyclerView recyclerView;

    @Override
    public String[] getArticleFilters() {
        return new String[]{"RecyclerView"};
    }

    @Override
    public String getToolBarTitle() {
        return "RecyclerView";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_recycler_view_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
        detailRVFragment.setSelectListener(this);
        fragmentSelect(detailRVFragment, detailRVFragment.getClass().getSimpleName());
        if (detailRVFragment instanceof SelectListener) {
            this.selectListener = (SelectListener) detailRVFragment;
        }
    }

    private void fragmentSelect(Fragment fragment, String tag) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.ui_activity_recycler_view_frame_layout, fragment, tag);
        transaction.commit();
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
