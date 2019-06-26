package com.wyq.firehelper.ui.android.recyclerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class LooperLayoutManager extends RecyclerView.LayoutManager {

    private boolean isLooperEnabled = true;

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {

        int distance = fill(dx, recycler, state);
        if (distance == 0) {
            return 0;
        }
        offsetChildrenHorizontal(distance * -1);

        recyclerHideView(dx,recycler,state);

        return distance;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        if (getItemCount() <= 0) {
            return;
        }

        if (state.isPreLayout()) {
            return;
        }

        //将所有的 itemView 从View树中全部detach，然后放入scrap缓存中。
        detachAndScrapAttachedViews(recycler);

        int actualWidth = 0;
        for (int i = 0; i < getItemCount(); i++) {
            //recycler.getViewForPosition(i)  方法会从缓存中拿到对应索引的 itemView，这个方法内部会先从 scrap 缓存中取 itemView，如果没有则从 recycler 缓存中取，如果还没有则调用 adapter 的 onCreateViewHolder() 去创建 itemView。
            View itemView = recycler.getViewForPosition(i);
            addView(itemView);
            measureChildWithMargins(itemView, 0, 0);
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);

            layoutDecorated(itemView, actualWidth, 0, actualWidth + width, height);

            actualWidth += width;
            if (actualWidth > getWidth()) {
                break;
            }

        }
    }

    /**
     * 设置是否启用无限循环
     * @param isLooperEnabled
     */
    public void setLooperEnabled(boolean isLooperEnabled){
        this.isLooperEnabled = isLooperEnabled;
    }


    /**
     * 填充视图
     *
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    private int fill(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dx > 0) {
            //向左滑动
            View lastView = getChildAt(getItemCount() - 1);
            if (lastView == null) {
                return 0;
            }
            int lastPos = getPosition(lastView);

            if (lastView.getRight() < getWidth()) {
                View scrap = null;
                if (lastPos == getItemCount() - 1) {
                    if (isLooperEnabled) {
                        scrap = recycler.getViewForPosition(0);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(lastPos + 1);
                }

                if (scrap == null) {
                    return dx;
                }

                addView(scrap);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, lastView.getRight(), 0, lastView.getRight() + width, height);
                return dx;

            }
        } else {

            //向右滑动
            View firstView = getChildAt(0);
            if (firstView == null) {
                return 0;
            }
            int firstPos = getPosition(firstView);

            if (firstView.getLeft() >= 0) {
                View scrap = null;
                if (firstPos == 0) {
                    if (isLooperEnabled) {
                        scrap = recycler.getViewForPosition(getItemCount() - 1);
                    } else {
                        dx = 0;
                    }
                } else {
                    scrap = recycler.getViewForPosition(firstPos - 1);
                }
                if (scrap == null) {
                    return 0;
                }
                addView(scrap, 0);
                measureChildWithMargins(scrap, 0, 0);
                int width = getDecoratedMeasuredWidth(scrap);
                int height = getDecoratedMeasuredHeight(scrap);
                layoutDecorated(scrap, firstView.getLeft() - width, 0,
                        firstView.getLeft(), height);
            }
        }
        return dx;
    }


    private void recyclerHideView(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < getItemCount(); i++) {
            View view = getChildAt(i);
            if (view == null) {
                continue;
            }

            if (dx > 0) {
                //向左滑动，回收不可见视图
                if (view.getRight() < 0) {
                    removeAndRecycleView(view, recycler);
                }
            } else {
                if (view.getLeft() > getWidth()) {
                    removeAndRecycleView(view, recycler);
                }
            }
        }
    }
}
