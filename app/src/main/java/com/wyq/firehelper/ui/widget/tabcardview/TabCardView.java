package com.wyq.firehelper.ui.widget.tabcardview;

import android.content.Context;
import android.util.AttributeSet;

import com.wyq.firehelper.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

/**
 * Author: Uni.W
 * Time: 2018/10/30 20:48
 * Desc:
 */
public class TabCardView extends CardView {
    public TabCardView(@NonNull Context context) {
        this(context,null);
    }

    public TabCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCardBackgroundColor(context.getResources().getColor(R.color.lightgray));

        setBackground(context.getDrawable(R.drawable.side_nav_bar));
        setRadius(5);
        setElevation(5);
    }


}
