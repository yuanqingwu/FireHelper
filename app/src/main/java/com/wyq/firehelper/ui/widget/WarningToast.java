package com.wyq.firehelper.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class WarningToast {

    public static final int WARN_YELLOW = Color.parseColor("#FFD700");
    public static final int ERROR_RED = Color.parseColor("#FF3333");

    private static Handler handler;
    private static Thread thread;
    private static Timer timer;

    public static void showCustomToast(Context context, String text, int textColor) {
        context = context.getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        // textView.setBackgroundColor(Color.GRAY);
        textView.setShadowLayer(10, 0, 0, textColor);
        textView.setTextColor(textColor);
        textView.setTextSize(16);
        layout.addView(textView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        // layout.setPadding(20, 20, 20, 20);
        

        if (Looper.myLooper() != null) {
            Toast toast = new Toast(context);
            toast.setView(layout);

            // 如果使用了缺省异常toast则居中显示
            if (textColor == ERROR_RED) {
                toast.setGravity(Gravity.CENTER, 0, 0);
            }
            toast.show();
        } else {
            if (Looper.myLooper() == null)
                Looper.prepare();
            Toast toast = new Toast(context);
            toast.setView(layout);

            // 如果使用了缺省异常toast则居中显示
            if (textColor == ERROR_RED) {
                toast.setGravity(Gravity.CENTER, 0, 0);
            }
            toast.show();
            Looper.loop();
        }
    }

    public static synchronized void showCustomToastContinuous(final Context context, final String text,
            final int textColor, final int period) {
        if (thread == null) {
            thread = new Thread(new Runnable() {
                public void run() {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }

                    handler = new Handler(Looper.myLooper()) {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);

                            if (msg.what == 0) {
                                if (msg.obj instanceof Timer) {
                                    timer = (Timer) msg.obj;
                                }
                                Context appContext = context.getApplicationContext();
                                LinearLayout layout = new LinearLayout(appContext);
                                layout.setOrientation(LinearLayout.VERTICAL);
                                TextView textView = new TextView(appContext);
                                textView.setText(text);
                                textView.setGravity(Gravity.CENTER);
                                // textView.setBackgroundColor(Color.GRAY);
                                textView.setShadowLayer(20, 2, 2, textColor);
                                textView.setTextColor(textColor);
                                textView.setTextSize(16);

                                textView.setOnLongClickListener(new OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View v) {
                                        if (timer != null) {
                                            timer.cancel();
                                            timer.purge();
                                            timer = null;
                                            if (handler != null) {
                                                handler.removeCallbacksAndMessages(null);
                                                handler = null;
                                            }
                                            if (thread != null) {
                                                if (!thread.isInterrupted())
                                                    thread.interrupt();
                                                thread = null;
                                            }
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    }
                                });

                                layout.addView(textView, new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                // layout.setPadding(20, 20, 20, 20);
                                final Toast toast = new Toast(appContext);
                                toast.setView(layout);

                                // 设置toast可点击
                                setToastClickable(toast);

                                // 如果使用了缺省异常toast则居中显示
                                if (textColor == ERROR_RED) {
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                }

                                toast.show();
                            }
                        }
                    };

                    Looper.loop();
                }
            });

            thread.start();

            // final Timer timer = new Timer();
            if (timer == null) {
                timer = new Timer();
            }

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    // toast.show();
                    if (handler != null) {
                        Message.obtain(handler, 0, timer).sendToTarget();
                    }
                }
            };
            if (timer != null) {
                timer.schedule(task, 0, period);
            }
        }
    }

    private static void setToastClickable(Toast toast) {
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

    private static Object getField(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }
}
