package com.wyq.firehelper.ui.android.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.ui.R;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class StickHeaderDecoration extends RecyclerView.ItemDecoration {

    private int contentLayout = 0;

    private Context context;
    private Paint paint;
    private float headTextHeight;
    private float headViewHeight;
    private float headTextPadding = 10;

    private String headText = "";

    public StickHeaderDecoration(Context context) {
        this.context = context;
        paint = new Paint();
        headTextHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, context.getResources().getDisplayMetrics());
        headViewHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
    }


    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        int pos = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition();
        View child = parent.findViewHolderForLayoutPosition(pos).itemView;
        Logger.i("pos:" + pos);
        boolean isTranslate = false;
        if ((pos % 10 == 0) && (child.getHeight() + child.getTop() < headViewHeight)) {
            canvas.save();
            isTranslate = true;
            float height = child.getHeight() + child.getTop() - headViewHeight;
            canvas.translate(0, height);
            Logger.i("height:" + height);
        }

        drawHeadView(parent, canvas);

        if (isTranslate) {
            canvas.restore();
        }
    }

    private void drawHeadText(RecyclerView parent, Canvas canvas) {
        float headHeight = headTextHeight + 2 * headTextPadding;

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(headTextHeight);
        paint.setAntiAlias(true);

        paint.setColor(context.getResources().getColor(R.color.colorPrimary));
        canvas.drawText(headText, parent.getX() + headTextPadding, headTextHeight + headTextPadding, paint);
    }

    private void drawHeadView(RecyclerView parent, Canvas canvas) {
        View topTitleView = LayoutInflater.from(context).inflate(getContentLayout(), parent, false);
        TextView titleText = (TextView) topTitleView.findViewById(R.id.recyclerview_item_title_tv);
        titleText.setText(headText);
        int toDrawWidthSpec;//用于测量的widthMeasureSpec
        int toDrawHeightSpec;//用于测量的heightMeasureSpec
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) topTitleView.getLayoutParams();
        if (lp == null) {
            lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//这里是根据复杂布局layout的width height，new一个Lp
            topTitleView.setLayoutParams(lp);
        }
        topTitleView.setLayoutParams(lp);
        if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            //如果是MATCH_PARENT，则用父控件能分配的最大宽度和EXACTLY构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.EXACTLY);
        } else if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //如果是WRAP_CONTENT，则用父控件能分配的最大宽度和AT_MOST构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
        } else {
            //否则则是具体的宽度数值，则用这个宽度和EXACTLY构建MeasureSpec
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(lp.width, View.MeasureSpec.EXACTLY);
        }
        //高度同理
        if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.EXACTLY);
        } else if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.AT_MOST);
        } else {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec((int) headViewHeight, View.MeasureSpec.EXACTLY);
        }
        //依次调用 measure,layout,draw方法，将复杂头部显示在屏幕上
        topTitleView.measure(toDrawWidthSpec, toDrawHeightSpec);
        topTitleView.layout(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getPaddingLeft() + topTitleView.getMeasuredWidth(), parent.getPaddingTop() + topTitleView.getMeasuredHeight());
        topTitleView.draw(canvas);//Canvas默认在视图顶部，无需平移，直接绘制
    }

    public void setHeadText(String headText) {
        this.headText = headText;
    }

    public void setContentLayout(int layoutId) {
        this.contentLayout = layoutId;
    }

    private int getContentLayout() {
        if (contentLayout != 0) {
            return contentLayout;
        } else {
            return R.layout.recyclerview_item_title;
        }
    }
}
