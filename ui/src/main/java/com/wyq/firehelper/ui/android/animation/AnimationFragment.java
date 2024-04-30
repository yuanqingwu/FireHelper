package com.wyq.firehelper.ui.android.animation;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.databinding.UiActivityAnimationLayoutBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewbinding.ViewBinding;

public class AnimationFragment extends BaseCaseFragment {

    public Button propertyButton;
    public Button tweenButton;
    public Button tweenButton1;
    public ImageView frameIv;

    private ValueAnimator valueAnimator, floatAnimator;
    private ObjectAnimator animator, animator1;


    @Override
    public String[] getArticleFilters() {
        return new String[]{"Animation"};
    }

    @Override
    public String getToolBarTitle() {
        return "Animation";
    }

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiActivityAnimationLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView(View view) {
        propertyButton = ((UiActivityAnimationLayoutBinding)binding).uiActivityAnimationPropertyBt;
        tweenButton = ((UiActivityAnimationLayoutBinding)binding).uiActivityAnimationTweenBt;
        tweenButton1 = ((UiActivityAnimationLayoutBinding)binding).uiActivityAnimationTweenBt1;
        frameIv = ((UiActivityAnimationLayoutBinding)binding).uiActivityAnimationFrameIv;

        animationProperty();
        animationTween();
        animationFrame();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animationProperty() {
        valueAnimator = ValueAnimator.ofInt(200, 600);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        final IntEvaluator intEvaluator = new IntEvaluator();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (Integer) animation.getAnimatedValue();
                float fraction = animation.getAnimatedFraction();//获取当前动画占整个动画过程的比例，0-1之间
                propertyButton.getLayoutParams().width = intEvaluator.evaluate(fraction,200,600);
                propertyButton.getLayoutParams().height = intEvaluator.evaluate(fraction,200,600);
                propertyButton.requestLayout();
            }
        });
        valueAnimator.start();

        floatAnimator = ValueAnimator.ofFloat(0, 1);
        floatAnimator.setDuration(2000);
        floatAnimator.setRepeatCount(ValueAnimator.INFINITE);
        floatAnimator.setRepeatMode(ValueAnimator.REVERSE);
        final FloatEvaluator evaluator = new FloatEvaluator();
        floatAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (Float) animation.getAnimatedValue();
                //获取当前动画占整个动画过程的比例，0-1之间
                float fraction = animation.getAnimatedFraction();
                propertyButton.setAlpha(evaluator.evaluate(fraction,0,1));
                propertyButton.setX(val * 100);
                propertyButton.setY(val * 100);
                propertyButton.requestLayout();
            }
        });
        floatAnimator.start();

        animator = ObjectAnimator.ofArgb(propertyButton, "textColor", 0xFFC1B6FF, 0xFF008000);
        animator.setDuration(2000);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();

        animator1 = ObjectAnimator.ofFloat(propertyButton, "rotation", 0, 360);
        animator1.setDuration(2000);
        animator1.setRepeatMode(ObjectAnimator.RESTART);
        animator1.setRepeatCount(ObjectAnimator.INFINITE);
        animator1.start();
    }

    private void animationFrame() {
        String[] drawableName = new String[]{"article", "module", "ui", "security", "tool", "architecture"};
        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (String s : drawableName) {
            int id = getResources().getIdentifier(s, "drawable", getActivity().getPackageName());
            animationDrawable.addFrame(getActivity().getResources().getDrawable(id), 150);
        }

        animationDrawable.setOneShot(false);
        frameIv.setImageDrawable(animationDrawable);
        animationDrawable.stop();
        animationDrawable.start();
    }

    private void animationTween() {

        //缩放
        Animation scaleAnimation = new ScaleAnimation(1, 2, 1, 2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setRepeatCount(ScaleAnimation.INFINITE);
        scaleAnimation.setRepeatMode(ScaleAnimation.REVERSE);

        //平移
        Animation translateAnimation = new TranslateAnimation(0, 0, -500, -100);
        translateAnimation.setDuration(2000);
        translateAnimation.setRepeatCount(ScaleAnimation.INFINITE);
        translateAnimation.setRepeatMode(ScaleAnimation.REVERSE);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new CollisionInterpolator());

        //旋转
        Animation rotateAnimation = new RotateAnimation(0f, 360f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(ScaleAnimation.INFINITE);
        rotateAnimation.setRepeatMode(ScaleAnimation.RESTART);

        //透明度
        Animation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatCount(ScaleAnimation.INFINITE);
        alphaAnimation.setRepeatMode(ScaleAnimation.REVERSE);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(scaleAnimation);
//        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(alphaAnimation);
        tweenButton.startAnimation(animationSet);

        tweenButton1.startAnimation(translateAnimation);

    }

    @Override
    public void onDestroy() {
        valueAnimator.cancel();
        valueAnimator = null;
        floatAnimator.cancel();
        floatAnimator = null;
        animator.cancel();
        animator = null;
        animator1.cancel();
        animator1 = null;

        super.onDestroy();
    }
}
