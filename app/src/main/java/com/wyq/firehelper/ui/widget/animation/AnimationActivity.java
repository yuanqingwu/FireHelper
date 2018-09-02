package com.wyq.firehelper.ui.widget.animation;

import android.animation.ValueAnimator;
import android.widget.Button;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;

import butterknife.BindView;

public class AnimationActivity extends BaseActivity {

    @BindView(R.id.ui_activity_animation_Bt)
    public Button button;


    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_animation_layout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        animationProperty();
    }

    private void animationProperty() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(200, 600);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                button.getLayoutParams().width = (Integer) animation.getAnimatedValue();
                button.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                button.requestLayout();
            }
        });
        valueAnimator.start();

        ValueAnimator floatAnimator = ValueAnimator.ofFloat(0, 1);
        floatAnimator.setDuration(2000);
        floatAnimator.setRepeatCount(ValueAnimator.INFINITE);
        floatAnimator.setRepeatMode(ValueAnimator.REVERSE);
        floatAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (Float) animation.getAnimatedValue();
                button.setAlpha(val);
                button.setX(val*100);
                button.setY(val*100);
                button.requestLayout();
            }
        });
        floatAnimator.start();
    }

    private void animationFrame() {


    }

    private void animationTweened() {


    }
}
