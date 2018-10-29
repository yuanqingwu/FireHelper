package com.wyq.firehelper.ui.widget.zoomimageview;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;

import butterknife.BindView;

public class ZoomImageViewFragment extends BaseCaseFragment {

    @BindView(R.id.zoom_viewpager)
    public ViewPager mViewPager;

    @BindView(R.id.ui_activity_image_view_photo_view)
    public PhotoView photoView;

    private int[] mImgs = new int[]{R.drawable.aurora1, R.drawable.aurora2,
            R.drawable.aurora3};

    private ImageView[] mImageViews = new ImageView[mImgs.length];


    @Override
    public String[] getArticleFilters() {
        return new String[]{"ZoomImageView"};
    }

    @Override
    public String getToolBarTitle() {
        return "ZoomImageView";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.zoom_imageview;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        photoView.setImageResource(mImgs[0]);

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ZoomImageView imageView = new ZoomImageView(
                        getContext());
//                imageView.setImageResource(mImgs[position]);
                Glide.with(getActivity()).load(mImgs[position]).apply(RequestOptions.fitCenterTransform()).into(imageView);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
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
