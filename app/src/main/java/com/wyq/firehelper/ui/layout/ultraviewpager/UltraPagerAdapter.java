package com.wyq.firehelper.ui.layout.ultraviewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyq.firehelper.R;

public class UltraPagerAdapter extends PagerAdapter {


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.ui_activity_ultra_view_pager_item,null);
        TextView textView = (TextView) layout.findViewById(R.id.ui_activity_ultra_view_pager_tv);
        textView.setText(position+"");
        container.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        LinearLayout layout = (LinearLayout) object;
        container.removeView(layout);
    }
}
