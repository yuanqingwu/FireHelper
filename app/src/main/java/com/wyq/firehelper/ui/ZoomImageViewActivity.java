package com.wyq.firehelper.ui;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.ui.widget.ZoomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ZoomImageViewActivity extends BaseActivity {

    @BindView(R.id.zoom_viewpager)
    public ViewPager mViewPager;

    @BindView(R.id.ui_activity_image_view_photo_view)
    public PhotoView photoView;

    private int[] mImgs = new int[]{R.drawable.aurora1, R.drawable.aurora2,
            R.drawable.aurora3};

    private ImageView[] mImageViews = new ImageView[mImgs.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zoom_imageview);
        ButterKnife.bind(this);

        photoView.setImageResource(mImgs[0]);

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
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mImageViews.length;
            }
        });


    }


}
