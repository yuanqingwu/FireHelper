package com.wyq.firehelper.ui.widget.zoomimageview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.databinding.UiFragmentZoomImageViewBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ZoomImageViewFragment extends BaseCaseFragment {

    public ViewPager mViewPager;

    public PhotoView photoView;

    private final int[] mImgs = new int[]{R.drawable.aurora1, R.drawable.aurora2,
            R.drawable.aurora3};

    private final ImageView[] mImageViews = new ImageView[mImgs.length];


    @Override
    public String[] getArticleFilters() {
        return new String[]{"ZoomImageView"};
    }

    @Override
    public String getToolBarTitle() {
        return "ZoomImageView";
    }


    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiFragmentZoomImageViewBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        mViewPager = ((UiFragmentZoomImageViewBinding)binding).zoomViewpager;
        photoView = ((UiFragmentZoomImageViewBinding)binding).uiActivityImageViewPhotoView;
        photoView.setImageResource(mImgs[0]);

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ZoomImageView imageView = new ZoomImageView(
                        getContext());
                imageView.setImageResource(mImgs[position]);

//                Glide.with(getActivity()).load(mImgs[position]).apply(RequestOptions.fitCenterTransform()).into(imageView);
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
