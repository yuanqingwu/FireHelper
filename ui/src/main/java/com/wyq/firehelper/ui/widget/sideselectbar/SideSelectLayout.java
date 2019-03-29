package com.wyq.firehelper.ui.widget.sideselectbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author Uni.W
 * @date 2019/3/29 14:03
 */
public class SideSelectLayout extends FrameLayout {

    private SideSelectBar selectBar;

    private float mTouchX;
    private float mTouchY;

    private OnItemSelectListenser onItemSelectListenser;

    public SideSelectLayout(Context context) {
        this(context, null);
    }

    public SideSelectLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideSelectLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnItemSelectListenser(OnItemSelectListenser onItemSelectListenser) {
        this.onItemSelectListenser = onItemSelectListenser;
    }

    private void init(Context context) {
        selectBar = new SideSelectBar(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
        selectBar.setLayoutParams(params);
        addView(selectBar);
        selectBar.setOnItemSelectListenser(new SideSelectBar.OnItemSelectListenser() {
            @Override
            public void onSelect(String item) {
                onItemSelectListenser.onSelect(item);
            }
        });
    }


    public void setNameList(List<String> nameList) {
        selectBar.setNameList(nameList);
    }

    public void setSelectItem(String item) {
        selectBar.setSelectName(item);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mTouchX = ev.getX();
        mTouchY = ev.getY();
        return selectBar.isTouchIn(mTouchX, mTouchY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        selectBar.dispatchTouchEvent(event);
        return true;
    }


    public interface OnItemSelectListenser {
        void onSelect(String item);
    }
}
