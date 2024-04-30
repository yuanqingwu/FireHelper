package com.wyq.firehelper.ui.android.bottomdialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.databinding.UiActivityBottomDialogLayoutBinding;
import com.wyq.firehelper.ui.widget.firetoast.FireToast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

public class BottomDialogFragment extends BaseCaseFragment implements View.OnClickListener {

    public Button button;
    public Button listBt;

    public Button okButton;
    public Button cancelButton;

    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiActivityBottomDialogLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    public String[] getArticleFilters() {
        return new String[]{"BottomDialog"};
    }

    @Override
    public String getToolBarTitle() {
        return "BottomDialog";
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        button = ((UiActivityBottomDialogLayoutBinding)binding).uiActivityBottomDialogShowBt;
        listBt = ((UiActivityBottomDialogLayoutBinding)binding).uiActivityBottomDialogShowListBt;
        button.setText("showBottomSheetDialog");
        button.setOnClickListener(this);

        listBt.setText("showListBottomSheetDialog");
        listBt.setOnClickListener(this);
    }

    private void initBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setContentView(R.layout.ui_dialog_bottom_dialog_layout);

        okButton = bottomSheetDialog.findViewById(R.id.ui_dialog_bottom_dialog_ok_bt);
        cancelButton = bottomSheetDialog.findViewById(R.id.ui_dialog_bottom_dialog_cancel_bt);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }


    public void initBottomSheetListDialog() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item: " + i);
        }

        BottomChooserDialog bottomChooserDialog = new BottomChooserDialog();

        bottomChooserDialog.build(getContext(), list, new BottomChooserDialog.OnItemChooseListener() {
            @Override
            public void onChoose(int position) {
                FireToast.instance(getContext()).setText("" + position).show();
                bottomChooserDialog.dismiss();
            }
        }).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.ui_activity_bottom_dialog_show_bt) {
            initBottomSheetDialog();
            bottomSheetDialog.show();

        } else if (i == R.id.ui_activity_bottom_dialog_show_list_bt) {
            initBottomSheetListDialog();
//            bottomSheetDialog.show();

        } else if (i == R.id.ui_dialog_bottom_dialog_ok_bt || i == R.id.ui_dialog_bottom_dialog_cancel_bt) {
            bottomSheetDialog.cancel();

        } else {
        }
    }
}
