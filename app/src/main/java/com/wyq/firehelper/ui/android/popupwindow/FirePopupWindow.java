package com.wyq.firehelper.ui.android.popupwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wyq.firehelper.R;

public class FirePopupWindow {

    private static FirePopupWindow firePopupWindow;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;


    private int orientation = HORIZONTAL;
    private String positiveText = "ok";
    private String negativeText = "cancel";

    private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int height = ViewGroup.LayoutParams.WRAP_CONTENT;
    private int xoff = 0;
    private int yoff = 0;
    private boolean isSetLocation = false;

    private boolean outsideTouchable = true;
    private boolean clippingEnabled = true;

    private Drawable backgroundDrawable = new ColorDrawable(Color.GRAY);

    private void reset() {
        orientation = HORIZONTAL;
        positiveText = "ok";
        negativeText = "cancel";

        width = ViewGroup.LayoutParams.WRAP_CONTENT;
        height = ViewGroup.LayoutParams.WRAP_CONTENT;
        xoff = 0;
        yoff = 0;
        isSetLocation = false;

        outsideTouchable = true;
        clippingEnabled = true;

        backgroundDrawable = new ColorDrawable(Color.GRAY);
    }

    /**
     * 根据text个数生成对应个数button
     *
     * @param text 一个或者两个text
     * @return
     */
    public static FirePopupWindow text(String... text) {
        if (firePopupWindow == null) {
            firePopupWindow = new FirePopupWindow();
        }
        firePopupWindow.reset();

        firePopupWindow.positiveText = "";
        firePopupWindow.negativeText = "";

        if (text != null && text.length > 0) {
            firePopupWindow.positiveText = text[0];
            if (text.length > 1) {
                firePopupWindow.negativeText = text[1];
            }
        }

        return firePopupWindow;
    }

    public FirePopupWindow setOrientation(int orientation) {
        this.orientation = orientation;
        return this;
    }

    public FirePopupWindow setOutsideTouchable(boolean outsideTouchable) {
        this.outsideTouchable = outsideTouchable;
        return this;
    }

    public FirePopupWindow setClippingEnabled(boolean clippingEnabled) {
        this.clippingEnabled = clippingEnabled;
        return this;
    }

    public FirePopupWindow setBackgroundDrawable(Drawable backgroundDrawable) {
        this.backgroundDrawable = backgroundDrawable;
        return this;
    }

    public FirePopupWindow setLayoutSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public FirePopupWindow setLocation(int xoff, int yoff) {
        this.xoff = xoff;
        this.yoff = yoff;
        this.isSetLocation = true;
        return this;
    }

    public FirePopupWindow showAsDropDown(View anchor) {
        Context context = anchor.getContext();
        LinearLayout popView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.ui_idalog_bottom_dialog_layout, null);
        popView.setOrientation(orientation);

        final PopupWindow popupWindow = new PopupWindow(popView, width, height);

        Button positiveBt = popView.findViewById(R.id.ui_dialog_bottom_dialog_ok_bt);
        Button negativeBt = popView.findViewById(R.id.ui_dialog_bottom_dialog_cancel_bt);

//        OnClickListener onClickListener = firePopupWindow.clickListener;
//        String text = firePopupWindow.positiveText;

        if (positiveText.length() == 0) {
            positiveBt.setVisibility(View.GONE);
        } else {
            positiveBt.setText(positiveText);
            positiveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(positiveText);
                    popupWindow.dismiss();
                }
            });
        }
        if (negativeText.length() == 0) {
            negativeBt.setVisibility(View.GONE);
        } else {
            negativeBt.setText(negativeText);
            negativeBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(negativeText);
                    popupWindow.dismiss();
                }
            });
        }

        popupWindow.setBackgroundDrawable(backgroundDrawable);
        popupWindow.setOutsideTouchable(outsideTouchable);
        popupWindow.setClippingEnabled(clippingEnabled);
        if (!isSetLocation) {
            xoff = anchor.getWidth() / 2;
            yoff = -anchor.getHeight() / 2;
        }
        popupWindow.showAsDropDown(anchor, xoff, yoff);
        return this;
    }

    public interface OnClickListener {
        void onClick(String text);
    }

    private OnClickListener clickListener;

    public FirePopupWindow setOnClickListener(OnClickListener listener) {
        this.clickListener = listener;
        return this;
    }
}
