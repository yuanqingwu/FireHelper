package com.wyq.firehelper.ui.widget.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.base.utils.common.BitmapUtils;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.R2;

import butterknife.BindView;

public class CircleImageViewFragment extends BaseCaseFragment {

    @BindView(R2.id.ui_activity_widget_circle_iamgeview_image)
    public ImageView imageView;
    @BindView(R2.id.ui_activity_widget_circle_iamgeview_image_alpha)
    public ImageView imageViewAlpha;
    @BindView(R2.id.ui_activity_widget_circle_iamgeview_text)
    public TextView textView;

    @Override
    public String[] getArticleFilters() {
        return new String[]{"自定义View"};
    }

    @Override
    public String getToolBarTitle() {
        return "CircleImageView";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_widget_circle_iamgeview;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.grassland);

        Bitmap blurImage = BitmapUtils.blur(bitmap, 100);

        imageViewAlpha.setImageBitmap(blurImage);
        imageViewAlpha.setFocusable(true);
        imageView.setImageBitmap(bitmap);
        imageViewAlpha.setOnTouchListener(new View.OnTouchListener() {
            private float startY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float lastY = event.getY();
                        float alphaDelt = (lastY - startY) / 8000;
                        float alpha = imageViewAlpha.getAlpha() + alphaDelt;
                        if (alpha > 1.0f) {
                            alpha = 1.0f;
                        } else if (alpha < 0.0f) {
                            alpha = 0.0f;
                        }
                        imageViewAlpha.setAlpha(alpha);
                        textView.setText(alpha + "");
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
