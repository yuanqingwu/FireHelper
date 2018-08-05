package com.wyq.firehelper.ui.layout.ultraViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;

import com.tmall.ultraviewpager.UltraViewPager;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UltraViewPagerActivity extends BaseActivity {

    @BindView(R.id.ui_activity_ultra_view_pager)
    public UltraViewPager ultraViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_ultra_view_pager_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        PagerAdapter adapter = new UltraPagerAdapter();
        ultraViewPager.setAdapter(adapter);

        ultraViewPager.initIndicator();
        ultraViewPager.getIndicator().setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()))
                .setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM)
                .build();

        ultraViewPager.setInfiniteLoop(true);
        ultraViewPager.setAutoScroll(2000);
        ultraViewPager.setMultiScreen(1.0f);

    }
}
