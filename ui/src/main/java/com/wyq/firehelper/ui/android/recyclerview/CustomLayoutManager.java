package com.wyq.firehelper.ui.android.recyclerview;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Uni.W
 * @date 2019/3/10 19:13
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
    }


    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }
}
