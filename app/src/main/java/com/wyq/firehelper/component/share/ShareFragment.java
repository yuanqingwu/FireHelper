package com.wyq.firehelper.component.share;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.databinding.ShareFragmentMainBinding;
import com.wyq.firehelper.ui.widget.firetoast.FireToast;

import static androidx.appcompat.app.AppCompatActivity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

/**
 * Author: Uni.W
 * Time: 2018/11/8 9:39
 * Desc:
 */
public class ShareFragment extends BaseCaseFragment {

    private static final int REQUEST_CODE = 0;

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
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return ShareFragmentMainBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        textView = ((ShareFragmentMainBinding)binding).shareFragmentFilePathTv;
        ((ShareFragmentMainBinding)binding).shareFragmentShareBt.setOnClickListener(v -> {
            String path = textView.getText().toString();
            if(path != null && !path.isEmpty()){
                FireShare.shareFileWithSys(getContext(),"share file",Uri.parse(path));
            }else{
                FireToast.instance(getContext()).setText("please select file first!").show();
            }
        });

        ((ShareFragmentMainBinding)binding).shareFragmentSelectFileBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType(FireShare.ContentType.FILE);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "choose file"), REQUEST_CODE);

            }
        });
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
