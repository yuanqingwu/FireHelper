package com.wyq.firehelper.ui.android.bottomdialog;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.ui.R;

import java.util.List;

/**
 * @author yuanqingwu
 * @date 2020/01/08
 */
public class BottomChooserDialog {

    private BottomSheetDialog bottomSheetDialog;

    public interface OnItemChooseListener {
        void onChoose(int position);
    }

    public BottomChooserDialog build(Context context, List<String> titleList, OnItemChooseListener chooseListener) {
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setContentView(R.layout.ui_dialog_bottom_dialog_list_layout);
        bottomSheetDialog.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);

        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.ui_dialog_bottom_dialog_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(titleList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                chooseListener.onChoose(position);
            }
        });
        return this;
    }

    public void show() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
        }
    }

    public void dismiss() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
    }
}
