package com.wyq.firehelper.ui.layout.pullextviewlayout;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.utils.CommonUtils;

import androidx.recyclerview.widget.RecyclerView;

public class ExtRecyclerViewLayout extends LinearLayout {

    private LoadingDot loadingDot;
    private RecyclerView headRecyclerView;
    private View headView;

    private Scroller scroller;

    private float headHeight = 0;
    private float headWidth = 0;

    private float dampingFactor = 1.0f;

    private int maxHeadHeight = 100;

    private float totalHeight = 0;

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
        //do nothing
        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                final int widthPadding;
                final int heightPadding;
                final int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
                final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    widthPadding = getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin;
                    heightPadding = getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin;
                } else {
                    widthPadding = getPaddingLeft() + getPaddingRight();
                    heightPadding = getPaddingTop() + getPaddingBottom();
                }

                final int desiredHeight = getMeasuredHeight() - heightPadding;
                if (child.getMeasuredHeight() < desiredHeight) {
                    final int childWidthMeasureSpec = getChildMeasureSpec(
                            widthMeasureSpec, widthPadding, lp.width);
//                    final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
//                            desiredHeight, MeasureSpec.EXACTLY);
                    final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding, lp.height);
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                }
            }


        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
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
        totalHeight = 0;
        for (int i = 0; i < childCount; i++) {
            totalHeight += getChildAt(i).getHeight();
        }
//        Logger.i("totalHeight:" + totalHeight);

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
                    //当滑动距离在头部长度之内并且向上滑动则直接关闭头部
                    if (scrollY > -headHeight && deltaY < 0)
                        resetHeadView(0);
                    else
                        resetHeadView((int) -headHeight);
                } else {//关
                    if (scrollY > getResetHeight())//如果上滑距离大于底部隐藏距离则，重置到底部刚好显示距离
                        resetHeadView(getResetHeight());
                    else if (scrollY < 0)//如果头部差一点没关掉就关掉
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

    private int getResetHeight() {
        int screenHeight = CommonUtils.getScreenHeight(getContext());
        int navigationBarHeight = CommonUtils.getWinHeight(getContext()) - screenHeight;
//        Logger.i(screenHeight + " nav:" + navigationBarHeight + " scrollY:" + getScrollY() + " getHeight:" + getHeight() + " getTop:" + getTop() + " getPaddingTop:" + getPaddingTop() + " getPaddingBottom:" + getPaddingBottom() + " getBottom:" + getBottom() + " headHeight:" + headHeight);
        float bottomHideHeight = navigationBarHeight + (totalHeight - headHeight + getTop() - screenHeight);

        if (bottomHideHeight < 0) {
            return 0;
        }
        return (int) bottomHideHeight;
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
        int scrollY = getScrollY();
        int absScrollY = Math.abs(scrollY);

        scrollBy(0, (int) (-dy / dampingFactor));

        if (dy < 0) {//向上滑动
//            resetHeadView(0);
            loadingDot.setVisibility(View.GONE);

            if (scrollY > 0) {
                dampingFactor = 2;
            }else{
                dampingFactor = 1;
            }
            return;
        }

        if (absScrollY <= headHeight) {
            dampingFactor = 1;
            if (scrollY < 0) {
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
