package com.wyq.firehelper.component.share;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.widget.firetoast.FireToast;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Author: Uni.W
 * Time: 2018/11/8 9:39
 * Desc:
 */
public class ShareFragment extends BaseCaseFragment {

    private static final int REQUEST_CODE = 0;

    @BindView(R.id.share_fragment_file_path_tv)
    public TextView textView;


    @Override
    public String[] getArticleFilters() {
        return new String[]{"Share"};
    }

    @Override
    public String getToolBarTitle() {
        return "Share";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.share_fragment_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @OnClick(R.id.share_fragment_share_bt)
    public void share(){
        String path = textView.getText().toString();
        if(path != null && !path.isEmpty()){
            FireShare.shareFileWithSys(getContext(),"share file",Uri.parse(path));
        }else{
            FireToast.instance(getContext()).setText("please select file first!").show();
        }
    }

    @OnClick(R.id.share_fragment_select_file_bt)
    public void selectFile() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType(FireShare.ContentType.FILE);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "choose file"), REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            textView.setText(uri.toString());
        }
    }
}
