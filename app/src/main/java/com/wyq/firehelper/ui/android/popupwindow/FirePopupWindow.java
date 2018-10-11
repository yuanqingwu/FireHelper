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
        firePopupWindow.orientation = orientation;
        return firePopupWindow;
    }

    public FirePopupWindow setOutsideTouchable(boolean outsideTouchable) {
        firePopupWindow.outsideTouchable = outsideTouchable;
        return firePopupWindow;
    }

    public FirePopupWindow setClippingEnabled(boolean clippingEnabled) {
        firePopupWindow.clippingEnabled = clippingEnabled;
        return firePopupWindow;
    }

    public FirePopupWindow setBackgroundDrawable(Drawable backgroundDrawable) {
        firePopupWindow.backgroundDrawable = backgroundDrawable;
        return firePopupWindow;
    }

    public FirePopupWindow setLayoutSize(int width, int height) {
        firePopupWindow.width = width;
        firePopupWindow.height = height;
        return firePopupWindow;
    }

    public FirePopupWindow setLocation(int xoff, int yoff) {
        firePopupWindow.xoff = xoff;
        firePopupWindow.yoff = yoff;
        firePopupWindow.isSetLocation = true;
        return firePopupWindow;
    }

    public FirePopupWindow showAsDropDown(View anchor) {
        Context context = anchor.getContext();
        LinearLayout popView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.ui_idalog_bottom_dialog_layout, null);
        popView.setOrientation(firePopupWindow.orientation);

        final PopupWindow popupWindow = new PopupWindow(popView, firePopupWindow.width, firePopupWindow.height);

        Button positiveBt = popView.findViewById(R.id.ui_dialog_bottom_dialog_ok_bt);
        Button negativeBt = popView.findViewById(R.id.ui_dialog_bottom_dialog_cancel_bt);

        if (firePopupWindow.positiveText.length() == 0) {
            positiveBt.setVisibility(View.GONE);
        } else {
            positiveBt.setText(firePopupWindow.positiveText);
            positiveBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firePopupWindow.clickListener.onClick(firePopupWindow.positiveText);
                    popupWindow.dismiss();
                }
            });
        }
        if (firePopupWindow.negativeText.length() == 0) {
            negativeBt.setVisibility(View.GONE);
        } else {
            negativeBt.setText(firePopupWindow.negativeText);
            negativeBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firePopupWindow.clickListener.onClick(firePopupWindow.negativeText);
                    popupWindow.dismiss();
                }
            });
        }

        popupWindow.setBackgroundDrawable(firePopupWindow.backgroundDrawable);
        popupWindow.setOutsideTouchable(firePopupWindow.outsideTouchable);
        popupWindow.setClippingEnabled(firePopupWindow.clippingEnabled);
        if (!firePopupWindow.isSetLocation) {
            firePopupWindow.xoff = anchor.getWidth() / 2;
            firePopupWindow.yoff = -anchor.getHeight() / 2;
        }
        popupWindow.showAsDropDown(anchor, firePopupWindow.xoff, firePopupWindow.yoff);
        return firePopupWindow;
    }

    public interface OnClickListener {
        void onClick(String text);
    }

    private OnClickListener clickListener;

    public FirePopupWindow setOnClickListener(OnClickListener listener) {
        firePopupWindow.clickListener = listener;
        return firePopupWindow;
    }
}
