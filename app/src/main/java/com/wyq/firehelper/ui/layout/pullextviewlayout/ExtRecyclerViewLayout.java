package com.wyq.firehelper.ui.layout.pullextviewlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.orhanobut.logger.Logger;

public class ExtRecyclerViewLayout extends LinearLayout {

    private LoadingDot loadingDot;
    private RecyclerView headRecyclerView;
    private View headView;

    private Scroller scroller;

    private float headHeight = 0;
    private float headWidth = 0;

    private float dampingFactor = 1.0f;

    private int maxHeadHeight = 100;

    private float lastX = 0;
    private float lastY = 0;
    private float lastInterceptX = 0;
    private float lastIntercepty = 0;
    float deltaY = 0;


    public ExtRecyclerViewLayout(Context context) {
        this(context, null);
    }

    public ExtRecyclerViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtRecyclerViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ExtRecyclerViewLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        scroller = new Scroller(context);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            Logger.i("hasWindowFocus");
            initViewSize();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
//        Logger.i("onMeasure childCount:" + childCount + " headHeight:" + headHeight);

        //do nothing
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int childCount = getChildCount();
//        Logger.i("onLayout childCount:" + childCount + " headHeight:" + headHeight + " changed:" + changed);

        //do nothing
    }

    /**
     * 初始化各项参数
     */
    private void initViewSize() {
        int childCount = getChildCount();
        if (childCount < 1) {
            return;
        }
        headView = getChildAt(0);
        if (headView instanceof ViewGroup) {
            int count = ((ViewGroup) headView).getChildCount();
            if (count > 1) {
                for (int i = 0; i < count; i++) {
                    View view = ((ViewGroup) headView).getChildAt(i);
                    if (view instanceof LoadingDot && loadingDot == null) {
                        loadingDot = (LoadingDot) view;
                    } else if (view instanceof RecyclerView && headRecyclerView == null) {
                        headRecyclerView = (RecyclerView) view;
                    }

                    maxHeadHeight += view.getMeasuredHeight();
                }

            }
        }
        headHeight = headView.getMeasuredHeight();
        headWidth = headView.getMeasuredWidth();
        if (getPaddingTop() != -(int) headHeight) {
            setPadding(0, -(int) headHeight, 0, 0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - lastInterceptX;
                float dy = y - lastIntercepty;

//                Logger.i("intercept " + "lastIntercepty:" + lastIntercepty + " maxheight:" + maxHeadHeight + " scrolly:" + getScrollY() + " gety:" + getY() + " gettop:" + getTop());
                if (Math.abs(dy) > Math.abs(dx)) {
                    intercept = true;
                } else {
                    intercept = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;

            default:
                break;
        }
        lastX = x;
        lastY = y;
        lastInterceptX = x;
        lastIntercepty = y;
//        Logger.i(intercept + "");
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:
                float dx = x - lastX;
                float dy = y - lastY;
//                Logger.i("intercept " + " maxheight:" + maxHeadHeight + " dy:" + dy + " scrolly:" + getScrollY() + " getTranslationY:" + getTranslationY() + " gety:" + getY() + " gettop:" + getTop());
                deltaY = dy;
                if (Math.abs(dy) > Math.abs(dx)) {
//                    if ((dy < 0 && getScrollY() <= 0) || (dy > 0 && getScrollY() >= -maxHeadHeight))
                    pullHeadView(dy);

                }

                break;
            case MotionEvent.ACTION_UP:
                int scrollY = getScrollY();
                float baseHeight = -headHeight / 2;
                if (scrollY <= baseHeight) {//开
                    resetHeadView((int) -headHeight);
                    //当滑动距离在头部长度之内并且向上滑动则直接关闭头部
                    if (scrollY > -headHeight && deltaY < 0)
                        resetHeadView(0);
                } else {//关
                    resetHeadView(0);
                }
                deltaY = 0;
                break;

            default:
                break;
        }

        lastX = x;
        lastY = y;
        return true;
    }

    private void resetHeadView(int y) {
        smoothScrollTo(y);
        if (y == 0) {//头部未显示
            loadingDot.setPercent(0);
//            loadingDot.setVisibility(View.VISIBLE);
        } else {//头部完全显示
            loadingDot.setPercent(1);
        }
        headRecyclerView.setTranslationY(0);
        loadingDot.setTranslationY(0);
        headView.setTranslationY(0);
    }

    private void pullHeadView(float dy) {
        scrollBy(0, (int) (-dy / dampingFactor));

        int scrollY = getScrollY();
        int absScrollY = Math.abs(scrollY);

        if (dy < 0) {//向上滑动
//            resetHeadView(0);
            loadingDot.setVisibility(View.GONE);
            return;
        }

        if (absScrollY <= headHeight) {
            dampingFactor = 1;
            float percent = absScrollY / headHeight;
            loadingDot.setPercent(percent);

            loadingDot.setVisibility(View.VISIBLE);
            if (percent < 0.5) {
                headRecyclerView.setTranslationY(-loadingDot.getMeasuredHeight());
            } else if (percent > 0.5) {
                float transY = headHeight * (percent - 0.5f);
                headRecyclerView.setTranslationY(transY - loadingDot.getMeasuredHeight());
                loadingDot.setTranslationY(transY);
            }
        } else {
            dampingFactor = 2;//阻尼增大
            loadingDot.setVisibility(View.GONE);
            float transY = (absScrollY - headHeight) / 2;
            headView.setTranslationY(-transY);
        }

    }

    private void smoothScrollTo(int y) {
        int scrollY = getScrollY();
        int dy = y - scrollY;
        scroller.startScroll(0, scrollY, 0, dy, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
