package com.wyq.firehelper.ui.widget.sideselectbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author Uni.W
 * @date 2019/3/29 14:03
 */
public class SideSelectBar extends View {

    private OnItemSelectListenser onItemSelectListenser;
    private List<String> nameList;
    private String selectName;
    private TextPaint textPaint;
    /**
     * 文字大小
     */
    private float textSize = 22;
    /**
     * 最大文字长度
     */
    private int textLenMax = 2;
    /**
     * 文字间的间隔
     */
    private float defaultPadding = 10;
    /**
     * 最大文字宽度
     */
    private float maxTextWidth;
    /**
     * 最大文字高度
     */
    private float maxTextHeight;
    private float mTouchX;
    private float mTouchY;

    public SideSelectBar(Context context) {
        this(context, null);
    }

    public SideSelectBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideSelectBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnItemSelectListenser(OnItemSelectListenser onItemSelectListenser) {
        this.onItemSelectListenser = onItemSelectListenser;
    }

    private void init() {
        nameList = new ArrayList<>();
        nameList.add("问");
        nameList.add("海阔天空");
        nameList.add("人生如梦1234");
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextAlign(Paint.Align.CENTER);

        maxTextWidth = calculateWidth(nameList);
        maxTextHeight = calculateHeight();
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
        invalidate();
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
        onItemSelectListenser.onSelect(selectName);
        invalidate();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        invalidate();
    }

    private float calculateWidth(List<String> nameList) {
        float maxWidth = 0;
        for (String name : nameList) {
            float len = textPaint.measureText(optimizeText(name));
            maxWidth = maxWidth > len ? maxWidth : len;
        }
        return maxWidth;
    }

    private float calculateHeight() {
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        return metrics.bottom - metrics.top;
    }

    private float getSingleLineHeight() {
        return maxTextHeight + defaultPadding * 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        float defaultWidth = maxTextWidth + getPaddingLeft() + getPaddingRight() + defaultPadding * 2;
        float defaultHeight = getSingleLineHeight() * nameList.size() + getPaddingTop() + getPaddingBottom();

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) defaultWidth, (int) defaultHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) defaultWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, (int) defaultHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float x = getPaddingLeft();
        float y = getPaddingTop();
        for (String name : nameList) {
            canvas.save();
            canvas.clipRect(x, y, x + getWidth(), y + getSingleLineHeight());

            if (name.equals(selectName)) {
                textPaint.setFakeBoldText(true);
                textPaint.setTextSize(textSize + 6);
            } else {
                textPaint.setFakeBoldText(false);
                textPaint.setTextSize(textSize);
            }
            canvas.drawText(optimizeText(name), getWidth() / 2, y - textPaint.getFontMetrics().ascent + defaultPadding, textPaint);
            canvas.restore();
            y += getSingleLineHeight();
        }
    }

    private String optimizeText(String text) {
        if (text != null && text.length() > textLenMax) {
            return text.substring(0, textLenMax);
        }
        return text;
    }

    private String findSelectItem(float y) {
        float dy = y - getTop() - getPaddingTop();
        int pos = (int) (dy / getSingleLineHeight());
        if (pos >= 0 && pos < nameList.size()) {
            return nameList.get(pos);
        }
        return null;
    }

    public boolean isTouchIn(float x, float y) {
        return x > getX() && x < (getX() + getWidth()) && y > getY() && y < getY() + getHeight();
    }

    private void reactSelectAction(float y) {
        setSelectName(findSelectItem(y));
        setBackgroundColor(Color.LTGRAY);
    }

    private void reactCancelAction() {
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = event.getX();
                mTouchY = event.getY();
                reactSelectAction(mTouchY);
                selectName = findSelectItem(mTouchY);
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchX = event.getX();
                mTouchY = event.getY();
                if (selectName == null || (selectName != null && !selectName.equals(findSelectItem(mTouchY)))) {
                    reactSelectAction(mTouchY);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                reactCancelAction();
                break;
            case MotionEvent.ACTION_UP:
                reactCancelAction();
                break;
            default:

                break;
        }
        return true;
    }

    public interface OnItemSelectListenser {
        void onSelect(String item);
    }
}
