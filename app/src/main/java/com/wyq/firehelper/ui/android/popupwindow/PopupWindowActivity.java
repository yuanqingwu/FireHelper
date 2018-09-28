package com.wyq.firehelper.ui.android.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PopupWindowActivity extends BaseActivity {

    @BindView(R.id.ui_activity_popup_window_bt)
    public Button button;
    @BindView(R.id.ui_activity_popup_window_bootom_bt)
    public Button bottomBt;

    public PopupWindow popupWindow;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_popup_window_layout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {

        button.setText("showPopupWindow");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });

        bottomBt.setText("showPopupWindowBottom");
        bottomBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindowBottom();
            }
        });
    }

    private void showPopupWindow(){
        View popView = LayoutInflater.from(this).inflate(R.layout.ui_idalog_bottom_dialog_layout, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setClippingEnabled(true);
        popupWindow.showAsDropDown(button,0,0);

//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM,0,0);
    }

    private void showPopupWindowBottom(){
        View popView = LayoutInflater.from(this).inflate(R.layout.ui_idalog_bottom_dialog_list_layout, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, 480);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setClippingEnabled(true);

        RecyclerView recyclerView = (RecyclerView)popupWindow.getContentView().findViewById(R.id.ui_dialog_bottom_dialog_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        List<String> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add("item: "+i);
        }
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);
//        popupWindow.showAsDropDown(button,0,0);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM,0,0);
    }
}
