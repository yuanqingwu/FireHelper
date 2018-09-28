package com.wyq.firehelper.ui.android.draglayout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

public class DragLayout extends LinearLayout {

    private ViewDragHelper dragHelper;

    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelperCallback());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        int action = ev.getActionMasked();
        if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL ){
            dragHelper.cancel();
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        dragHelper.processTouchEvent(event);

        return true;
    }

    private class ViewDragHelperCallback extends ViewDragHelper.Callback{

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {

            return true;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            Logger.i("left:"+left+" dx:"+dx);
            return Math.min(Math.max(left,getPaddingLeft()),getWidth()-child.getWidth());
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            Logger.i("top:"+top+" dy:"+dy);
            return Math.min(Math.max(top,getPaddingTop()),getHeight()-child.getHeight());
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

    }
}
