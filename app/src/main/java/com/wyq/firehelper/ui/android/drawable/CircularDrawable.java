package com.wyq.firehelper.ui.android.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CircularDrawable extends Drawable {

    private Paint paint;

    public CircularDrawable(int color) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect rect = getBounds();
        float cx = rect.exactCenterX();
        float cy = rect.exactCenterY();
        canvas.drawCircle(cx, cy, Math.min(cx, cy), paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
