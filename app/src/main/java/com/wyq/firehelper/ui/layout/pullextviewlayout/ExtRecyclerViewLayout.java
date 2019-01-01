package com.wyq.firehelper.ui.layout.pullextviewlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.wyq.firehelper.utils.common.ScreenUtils;

import androidx.recyclerview.widget.RecyclerView;

public class ExtRecyclerViewLayout extends LinearLayout {


    private LoadingDot loadingDot;
    /**
     * 下拉时先上移loadingDot高度再缓缓下移
     */
    private RecyclerView headRecyclerView;
    private View headView;
    private Scroller scroller;
    /**
     * 头部长度
     */
    private float headHeight = 0;
    /**
     * 布局总长度，
     */
    private float totalHeight = 0;
    /**
     * 滑动阻尼
     */
    private float dampingFactor = 1.0f;
    /**
     * 每次滑动距离，向下为正
     */
    private float deltaY = 0;
    private float lastX = 0;
    private float lastY = 0;
    private float lastInterceptX = 0;
    private float lastIntercepty = 0;

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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Logger.i("onSizeChanged " + h + "  oldHeight:" + oldh);
        initViewSize();
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasWindowFocus) {
//        super.onWindowFocusChanged(hasWindowFocus);
//        if (hasWindowFocus) {
//            Logger.i("hasWindowFocus");
//            initViewSize();
//        }
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = 0;
        int measuredHeight = 0;
        int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            measuredWidth = getChildAt(0).getMeasuredWidth();
            for (int i = 0; i < childCount; i++) {
                measuredHeight += getChildAt(i).getMeasuredHeight();
            }
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            measuredWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(measuredWidth, heightSpaceSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            for (int i = 0; i < childCount; i++) {
                measuredHeight += getChildAt(i).getMeasuredHeight();
            }
            setMeasuredDimension(widthSpaceSize, measuredHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //do nothing
//        initViewSize();
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
            totalHeight += getChildAt(i).getMeasuredHeight();
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
                }
            }
        }
        headHeight = headView.getMeasuredHeight();
        if (getPaddingTop() != -(int) headHeight) {
            setPadding(0, -(int) headHeight, 0, 0);
        }

//        Logger.i("totalHeight:" + totalHeight + " headHeight:" + headHeight);
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
                if (scrollY <= baseHeight) {
                    //开
                    //当滑动距离在头部长度之内并且向上滑动则直接关闭头部
                    if (scrollY > -headHeight && deltaY < 0) {
                        resetHeadView(0);
                    } else {
                        resetHeadView((int) -headHeight);
                    }
                } else {
                    //关
                    //如果上滑距离大于底部隐藏距离则，重置到底部刚好显示距离
                    if (scrollY > getResetHeight()) {
                        resetHeadView(getResetHeight());
                    } else if (scrollY < 0) {
                        //如果头部差一点没关掉就关掉
                        resetHeadView(0);
                    }
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
        int screenHeight = ScreenUtils.getHeightPX(getContext());
        int navigationBarHeight = ScreenUtils.getNavigationBarHeight(getContext());//即使是实体按键，这个也是有虚拟数值的
//        Logger.i(screenHeight + " nav:" + navigationBarHeight + " scrollY:" + getScrollY() + " getHeight:" + getHeight() + " getTop:" + getTop() + " getPaddingTop:" + getPaddingTop() + " getPaddingBottom:" + getPaddingBottom() + " getBottom:" + getBottom() + " headHeight:" + headHeight);
        float bottomHideHeight = navigationBarHeight + (totalHeight - headHeight + getTop() - screenHeight);

//        Logger.i("bottomHideHeight:" + bottomHideHeight);
        if (bottomHideHeight < 0) {
            return 0;
        }
        return (int) bottomHideHeight;
    }

    private void resetHeadView(int y) {
        smoothScrollTo(y);
        if (y >= 0) {//头部未显示
            loadingDot.setPercent(0);
        } else {//头部完全显示
            loadingDot.setPercent(1);
        }
        headRecyclerView.setTranslationY(0);
        loadingDot.setTranslationY(0);
        if (getScrollY() > -headHeight) {
            headView.setTranslationY(0);
        } else {
            //如果下拉超出头部高度，需要特殊处理，1/2速度上移,见computeScroll（）
        }
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
            } else {
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
            int scrollY = scroller.getCurrY();
            scrollTo(scroller.getCurrX(), scrollY);
            if (scrollY < -headHeight) {//下拉超出头部高度时，以1/2速度上移
                headView.setTranslationY((scrollY + headHeight) / 2);
            }
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
