package com.wyq.firehelper.ui.android.popupwindow;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class PopupWindowFragment extends BaseCaseFragment {

    @BindView(R.id.ui_activity_popup_window_bt)
    public Button button;
    @BindView(R.id.ui_activity_popup_window_bootom_bt)
    public Button bottomBt;

    public PopupWindow popupWindow;

    @Override
    public String[] getArticleFilters() {
        return new String[]{"PopupWindow"};
    }

    @Override
    public String getToolBarTitle() {
        return "PopupWindow";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_popup_window_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
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

    private void showPopupWindow() {
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.ui_dialog_bottom_dialog_layout, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setClippingEnabled(true);
        popupWindow.showAsDropDown(button, 0, 0);

//        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM,0,0);
    }

    private void showPopupWindowBottom() {
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.ui_dialog_bottom_dialog_list_layout, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, 480);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setClippingEnabled(true);

        RecyclerView recyclerView = (RecyclerView) popupWindow.getContentView().findViewById(R.id.ui_dialog_bottom_dialog_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item: " + i);
        }
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);
//        popupWindow.showAsDropDown(button,0,0);
        popupWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
}
