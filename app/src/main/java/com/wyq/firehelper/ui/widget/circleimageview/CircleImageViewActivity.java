package com.wyq.firehelper.ui.widget.circleimageview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.ui.BitmapUtils;

/**
 * Created by wuyuanqing on 2015/9/8.
 */
public class CircleImageViewActivity extends BaseActivity {
    private ImageView imageView,imageViewAlpha;
    private TextView textView;

    @Override
    protected int attachLayoutRes() {
        return R.layout.circle_iamgeview;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        imageView = (ImageView) findViewById(R.id.image);
        imageViewAlpha=(ImageView)findViewById(R.id.image_alpha);
        textView=(TextView)findViewById(R.id.text);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.grassland);

        Bitmap blurImage= BitmapUtils.blur(bitmap, 100);

        imageViewAlpha.setImageBitmap(blurImage);
        imageViewAlpha.setFocusable(true);
        imageView.setImageBitmap(bitmap);
        imageViewAlpha.setOnTouchListener(new View.OnTouchListener() {
            private float startY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY=event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float lastY=event.getY();
                        float alphaDelt=(lastY-startY)/8000;
                        float alpha=imageViewAlpha.getAlpha()+alphaDelt;
                        if(alpha>1.0f){
                            alpha=1.0f;
                        }else if(alpha<0.0f){
                            alpha=0.0f;
                        }
                        imageViewAlpha.setAlpha(alpha);
                        textView.setText(alpha+"");
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
