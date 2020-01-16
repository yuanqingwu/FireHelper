package com.wyq.firehelper.ui.android.recyclerview.snaphelper;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanqingwu
 * @date 2020/01/16
 */
public class SnapHelperFragment extends BaseCaseFragment {

    private RecyclerView recyclerView;

    private List<String> nameList = new ArrayList<>();

    @Override
    public String[] getArticleFilters() {
        return new String[0];
    }

    @Override
    public String getToolBarTitle() {
        return "SnapHelper";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_fragment_snap_helper;
    }

    @Override
    protected void initData() {
        nameList.clear();
        for (int i = 0; i < 30; i++) {
            nameList.add("TEST " + i);
        }
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.ui_fragment_recycler_view_snap_helper_rv);

        SnapHelperAdapter adapter = new SnapHelperAdapter(nameList);
        adapter.setLayoutParams(new ViewGroup.LayoutParams(200, 300));
        adapter.setBackgroundColor(Color.GRAY);
        int space = 20;
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int adapterPosition = parent.getChildAdapterPosition(view);
                if (adapterPosition == 0) {
                    outRect.set(space, 0, space / 2, 0);
                } else if (adapterPosition == adapter.getItemCount() - 1) {
                    outRect.set(space / 2, 0, space, 0);
                } else {
                    outRect.set(space / 2, 0, space / 2, 0);
                }
            }
        });
        recyclerView.setAdapter(adapter);

//        LinearSnapHelper snapHelper = new LinearSnapHelper();//没有计算ItemDecoration的大小

        GravitySnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);

    }
}
