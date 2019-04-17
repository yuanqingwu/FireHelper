package com.wyq.firehelper.base.swipeback;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * refer to :
 * https://github.com/YoKeyword/Fragmentation/tree/master/fragmentation_swipeback
 * https://github.com/ikew0ng/SwipeBackLayout
 */
public class SwipeBackLayout extends FrameLayout {


    public SwipeBackLayout(@NonNull Context context) {
        this(context,null);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeBackLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




}
