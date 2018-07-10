package com.wyq.firehelper.ui;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.activity.BaseActivity;
import com.wyq.firehelper.ui.widget.ZoomImageView;


public class ZoomImageViewActivity extends BaseActivity {

    protected ViewPager mViewPager;

    private int[] mImgs = new int[]{R.mipmap.aurora1, R.mipmap.aurora2,
            R.mipmap.aurora3};

    private ImageView[] mImageViews = new ImageView[mImgs.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_imageview);

        mViewPager = (ViewPager) findViewById(R.id.zoom_viewpager);
        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ZoomImageView imageView = new ZoomImageView(
                        getApplicationContext());
                imageView.setImageResource(mImgs[position]);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mImageViews[position]);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return mImageViews.length;
            }
        });


    }


}
