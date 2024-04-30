package com.wyq.firehelper.ui.widget.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.databinding.UiActivityWidgetCircleIamgeviewBinding;

public class CircleImageViewFragment extends BaseCaseFragment {

    public ImageView imageView;

    @Override
    public String[] getArticleFilters() {
        return new String[]{"自定义View"};
    }

    @Override
    public String getToolBarTitle() {
        return "CircleImageView";
    }

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiActivityWidgetCircleIamgeviewBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        imageView = ((UiActivityWidgetCircleIamgeviewBinding)binding).uiActivityWidgetCircleIamgeviewImage;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.grassland);
        imageView.setImageBitmap(bitmap);
    }
}
