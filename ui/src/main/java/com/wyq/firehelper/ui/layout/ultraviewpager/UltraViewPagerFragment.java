package com.wyq.firehelper.ui.layout.ultraviewpager;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmall.ultraviewpager.UltraViewPager;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.databinding.UiActivityUltraViewPagerLayoutBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.PagerAdapter;

public class UltraViewPagerFragment extends BaseCaseFragment {

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
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiActivityUltraViewPagerLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        ultraViewPager = ((UiActivityUltraViewPagerLayoutBinding)binding).uiActivityUltraViewPager;

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
