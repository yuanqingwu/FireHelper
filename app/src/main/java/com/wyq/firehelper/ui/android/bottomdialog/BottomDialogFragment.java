package com.wyq.firehelper.ui.android.bottomdialog;

import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class BottomDialogFragment extends BaseCaseFragment implements View.OnClickListener {

    @BindView(R.id.ui_activity_bottom_dialog_show_bt)
    public Button button;
    @BindView(R.id.ui_activity_bottom_dialog_show_list_bt)
    public Button listBt;

    public Button okButton;
    public Button cancelButton;

    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_bottom_dialog_layout;
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

        okButton = (Button) bottomSheetDialog.findViewById(R.id.ui_dialog_bottom_dialog_ok_bt);
        cancelButton = (Button) bottomSheetDialog.findViewById(R.id.ui_dialog_bottom_dialog_cancel_bt);
        okButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }


    public void initBottomSheetListDialog(){
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.setContentView(R.layout.ui_dialog_bottom_dialog_list_layout);
        RecyclerView recyclerView = (RecyclerView)bottomSheetDialog.findViewById(R.id.ui_dialog_bottom_dialog_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        List<String> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add("item: "+i);
        }
        TvRecyclerViewAdapter adapter = new TvRecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ui_activity_bottom_dialog_show_bt:
                initBottomSheetDialog();
                bottomSheetDialog.show();
                break;
            case R.id.ui_activity_bottom_dialog_show_list_bt:
                initBottomSheetListDialog();
                bottomSheetDialog.show();
                break;
            case R.id.ui_dialog_bottom_dialog_ok_bt:
            case R.id.ui_dialog_bottom_dialog_cancel_bt:
                bottomSheetDialog.cancel();
                break;
            default:
                break;
        }
    }
}
