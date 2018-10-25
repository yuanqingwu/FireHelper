package com.wyq.firehelper.ui.layout.ultraviewpager;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.tmall.ultraviewpager.UltraViewPager;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

import butterknife.BindView;

public class UltraViewPagerFragment extends BaseCaseFragment {

    @BindView(R.id.ui_activity_ultra_view_pager)
    public UltraViewPager ultraViewPager;

    @Override
    public String[] getArticleFilters() {
        return new String[]{"UltraViewPager"};
    }

    @Override
    public String getToolBarTitle() {
        return "UltraViewPager";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_ultra_view_pager_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
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
