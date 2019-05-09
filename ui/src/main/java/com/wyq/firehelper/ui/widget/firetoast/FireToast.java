package com.wyq.firehelper.ui.widget.firetoast;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.ColorInt;

/**
 * @author Uni.W
 * @time 2018/10/29 22:05
 * @describe 一种功能更多的Toast封装，支持图片，连续显示，双击等功能
 */

public class FireToast {
    public static final int WARN_YELLOW = Color.parseColor("#FFD700");
    public static final int ERROR_RED = Color.parseColor("#FF3333");

    /**
     * 停止标志位
     */
    private volatile static boolean isStop = false;
    private static SoftReference<HashMap<String, FireToast>> softToastList;
    private int gravity = Gravity.BOTTOM;
    private int textSize = 16;
    @ColorInt
    private int textColor = Color.GRAY;
    private boolean showShadowLayer;
    private String text;
    private Drawable headImg;
    private int headImgWidth = 40;
    private int headImgHeight = 40;
    private long startTime;
    private Context context;
    private OnDoubleClickListener onDoubleClickListener;
    private HashMap<String, FireToast> toastList;

    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    private FireToast(Context context, String name) {
        this.context = context;

        if (softToastList == null) {
            toastList = new HashMap<>();
            softToastList = new SoftReference<>(toastList);
        }
        toastList = softToastList.get();
        if (toastList == null) {
            toastList = new HashMap<>();
            softToastList = new SoftReference<>(toastList);
        }

    }

    public static FireToast instance(Context context, String name) {
        FireToast fireToast = new FireToast(context, name);
        fireToast.addInstance(name, fireToast);
        return fireToast;
    }

    public static FireToast instance(Context context) {
        FireToast fireToast = new FireToast(context, null);
        return fireToast;
    }

    public static FireToast get(String name) {
        FireToast fireToast = null;
        if (softToastList != null) {
            HashMap<String, FireToast> liveToastList = softToastList.get();
            if (liveToastList != null) {
                fireToast = liveToastList.get(name);
            }
        }
        return fireToast;
    }

    private void addInstance(String name, FireToast fireToast) {
        if (toastList != null) {
            toastList.put(name, fireToast);
        }
    }

    public FireToast setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
        return this;
    }

    /**
     * 弹出简单吐司
     * @param msg
     */
    public void msg(String msg){
        if(Looper.myLooper() != null){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }else{
            mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    msg(msg);
                }
            });
        }
    }

    public String getText() {
        return text;
    }

    public FireToast setText(String text) {
        this.text = text;
        return this;
    }

    public int getGravity() {
        return gravity;
    }

    public FireToast setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public int getTextSize() {
        return textSize;
    }

    public FireToast setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public FireToast setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public boolean isShowShadowLayer() {
        return showShadowLayer;
    }

    public FireToast setShowShadowLayer(boolean showShadowLayer) {
        this.showShadowLayer = showShadowLayer;
        return this;
    }

    public Drawable getHeadImg() {
        return headImg;
    }

    public FireToast setHeadImg(Drawable headImg) {
        this.headImg = headImg;
        return this;
    }

    public int getHeadImgWidth() {
        return headImgWidth;
    }

    public void setHeadImgWidth(int headImgWidth) {
        this.headImgWidth = headImgWidth;
    }

    public int getHeadImgHeight() {
        return headImgHeight;
    }

    public void setHeadImgHeight(int headImgHeight) {
        this.headImgHeight = headImgHeight;
    }

    private void initView(Toast toast) {
        Context appContext = context.getApplicationContext();
        LinearLayout layout = new LinearLayout(appContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);

        //是否设置了图片
        if (headImg != null) {
            ImageView imageView = new ImageView(appContext);
            imageView.setImageDrawable(headImg);
            layout.addView(imageView, headImgWidth, headImgHeight);
        }

        TextView textView = new TextView(appContext);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        // textView.setBackgroundColor(Color.GRAY);
        if (showShadowLayer) {
            textView.setShadowLayer(20, 2, 2, textColor);
        }
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);

        final FireToast fireToast = this;
        if (onDoubleClickListener != null) {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (System.currentTimeMillis() - startTime < 2000) {
                        onDoubleClickListener.onDoubleClick(fireToast);
                    }
                    startTime = System.currentTimeMillis();
                }
            });
        }
        layout.addView(textView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        toast.setView(layout);
    }

    public void show() {
        boolean haveLooper = true;
        if (Looper.myLooper() == null) {
            haveLooper = false;
            Looper.prepare();
        }

        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        if (!haveLooper) {
            Looper.loop();
        }

    }

    public void showCustomToast() {
        boolean haveLooper = true;
        if (Looper.myLooper() == null) {
            haveLooper = false;
            Looper.prepare();
        }
        Toast toast = new Toast(context);
        initView(toast);

        // 如果使用了缺省异常toast则居中显示
        if (textColor == ERROR_RED) {
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        toast.show();
        if (!haveLooper) {
            Looper.loop();
        }

    }

    public synchronized void showCustomToastContinuous(int period, int delay) {
        isStop = false;

        final Timer timer = new Timer();
        final Toast toast = new Toast(context);
        initView(toast);
        setToastClickable(toast);
        // 如果使用了缺省异常toast则居中显示
        if (textColor == ERROR_RED) {
            toast.setGravity(Gravity.CENTER, 0, 0);
        }

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Logger.i(text + Thread.currentThread().getName());
                if (isStop) {
                    cancel();
                    timer.cancel();
                } else {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    toast.show();
                    Looper.myLooper().quit();
                    Looper.loop();
                }
            }
        };

        timer.schedule(timerTask, delay, period);
    }

    public void cancel() {
        isStop = true;
    }

    private void setToastClickable(Toast toast) {
        try {
            Object mTN;
            mTN = getField(toast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    // Toast可点击
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

                    // 设置viewgroup宽高
                    // params.width = WindowManager.LayoutParams.MATCH_PARENT; //设置Toast宽度为屏幕宽度
                    // params.height = WindowManager.LayoutParams.WRAP_CONTENT; //设置高度
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    public interface OnDoubleClickListener {
        void onDoubleClick(FireToast fireToast);
    }

}
