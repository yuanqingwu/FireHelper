package com.wyq.firehelper.ui.layout.pullextviewlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LoadingDot extends View {
    private Paint paint;
    private Context context;
    private boolean shouldVibrator = true;

    //default size in 160dpi
    private float normalRadius = 3.0f;
    private float maxRadius = 2 * normalRadius;
    private float maxDistance = 8 * normalRadius;
    private float percent = 0.0f;
    private int color = Color.GRAY;

    //一个小圆点开始分裂为三个的百分比阈值
    private float percentThresholdSplit = 0.25f;
    //三个小圆点开始慢慢消失的百分比阈值
    private float percentThresholdHide = 0.5f;


    public LoadingDot(Context context) {
        this(context, null);
    }

    public LoadingDot(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingDot(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingDot(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initParam();
        initPaint();
    }

    /**
     * 为不同分辨率的屏幕设置不同的大小
     */
    private void initParam() {
        float density = context.getResources().getDisplayMetrics().density;
//        Logger.i("density:" + density);
        normalRadius = density * normalRadius;
        maxRadius = 2 * normalRadius;
        maxDistance = 8 * normalRadius;
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getColor());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         *三个阶段：
         * 1.[0-0.25]一个小点半径慢慢变大直到最大半径
         * 2.[0.25-0.5]小点中分散出左右两个小点，并慢慢远离
         */
        float cx = getWidth() / 2;
        float cy = getHeight() * (1 - percent);
        setAlpha(1);
        if (getPercent() > 0 && getPercent() <= percentThresholdSplit) {
            canvas.drawCircle(cx, cy, maxRadius * percent / percentThresholdSplit, paint);
        } else if (getPercent() > percentThresholdSplit && getPercent() <= percentThresholdHide) {
            float mDistance = maxDistance * (percent - percentThresholdSplit) * 2;
            canvas.drawCircle(cx - mDistance, cy, normalRadius, paint);
            canvas.drawCircle(cx, cy, maxRadius * (1 + percentThresholdHide - percent * 2), paint);
            canvas.drawCircle(cx + mDistance, cy, normalRadius, paint);
        } else if (getPercent() > percentThresholdHide && getPercent() <= 1) {
            if (shouldVibrator) {
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(20);
                shouldVibrator = false;
            }
            cy = getHeight() * percentThresholdHide;
            float alpha = (1 - (percent - percentThresholdHide) * 2);//两倍速率消失
//            paint.setAlpha(alpha);//255-0
            canvas.drawCircle(cx - maxDistance, cy, normalRadius, paint);
            canvas.drawCircle(cx, cy, normalRadius, paint);
            canvas.drawCircle(cx + maxDistance, cy, normalRadius, paint);
            setAlpha(alpha);
        }

    }

    public float getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(float maxRadius) {
        this.maxRadius = maxRadius;
    }

    public float getNormalRadius() {
        return normalRadius;
    }

    public void setNormalRadius(float normalRadius) {
        this.normalRadius = normalRadius;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
        if (percent == 0) {
            shouldVibrator = true;
        }
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
