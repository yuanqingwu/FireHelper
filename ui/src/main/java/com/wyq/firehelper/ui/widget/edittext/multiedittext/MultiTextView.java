package com.wyq.firehelper.ui.widget.edittext.multiedittext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class MultiTextView extends AppCompatTextView implements MultiEditText {

    private int strokeColor;

    private TextView backgroundTextView;

    public MultiTextView(Context context) {
        this(context, null);
    }

    public MultiTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backgroundTextView = new TextView(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        strokeColor = Color.CYAN;
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        backgroundTextView.setLayoutParams(params);
        super.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        backgroundTextView.measure(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        backgroundTextView.layout(left, top, right, bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CharSequence text = backgroundTextView.getText();
        if (text == null || !text.equals(getText())) {
            backgroundTextView.setText(getText());
        }
        backgroundTextView.draw(canvas);

        super.onDraw(canvas);

    }

    @Override
    public void setTextEffect(TextEffect effect) {
        switch (effect) {
            case CLASSIC:

                break;
            case BOLD:
                TextPaint paint = getPaint();
                paint.setFakeBoldText(true);
                break;
            case NEON:
                setShadowLayer(5, 5, 5, getCurrentTextColor());
                break;
            case STROKE:
                TextPaint backgroundPaint = backgroundTextView.getPaint();
                backgroundPaint.setStrokeWidth(2);
                backgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                backgroundTextView.setTextColor(strokeColor);
                break;
            default:
                break;
        }
        invalidate();
    }

}
