package com.wyq.firehelper.ui.widget.fireseekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSeekBar;

/**
 * 1.拉动有气泡提示进度
 * 2.可设置刻度点图标
 *
 * @author Uni.W
 * @date 2018/11/22 15:56
 */
public class FireSeekBar extends AppCompatSeekBar {


    public FireSeekBar(Context context) {
        this(context,null);
    }

    public FireSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FireSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        super.setOnSeekBarChangeListener(l);
    }
}
