package com.wyq.firehelper.java.aop.aspectj;

import android.view.View;

import com.wyq.firehelper.utils.LogUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SingleClickAspect {
    static int TIME_TAG = 0;
    public static final int MIN_CLICK_DELAY_TIME = 600;//间隔时间600ms
    long lastClickTime = 0;

    @Pointcut("execution(@com.wyq.firehelper.java.aop.aspectj.FireSingleClick * *(..))")
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs())
            if (arg instanceof View) {
                view = (View) arg;
            }
        if (view != null) {
            Object tag = view.getTag(view.getId());
            long lastClickTime = ((tag != null) ? (long) tag : 0);
            LogUtils.i("SingleClickAspect", "lastClickTime:" + lastClickTime);
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
                view.setTag(view.getId(), currentTime);
                LogUtils.i("SingleClickAspect", "currentTime:" + currentTime);
                joinPoint.proceed();//执行原方法
            } else {
                LogUtils.e("SingleClickAspect", "ignore too quick click");
            }
        }
    }
}