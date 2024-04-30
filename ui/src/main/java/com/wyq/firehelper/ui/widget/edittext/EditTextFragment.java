package com.wyq.firehelper.ui.widget.edittext;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.databinding.UiFragmentMultiEditTextBinding;
import com.wyq.firehelper.ui.widget.edittext.limitcountedittext.CountEditTextLayout;
import com.wyq.firehelper.ui.widget.edittext.multiedittext.MultiEditText;
import com.wyq.firehelper.ui.widget.edittext.multiedittext.MultiTextView;

/**
 * @author yuanqingwu
 * @date 2019/05/09
 */
public class EditTextFragment extends BaseCaseFragment {

    public MultiTextView multiTextView;
    public MultiTextView multiTextView1;
    public MultiTextView multiTextView2;
    public MultiTextView multiTextView3;

    public CountEditTextLayout countLimitEt;

    @Override
    public String[] getArticleFilters() {
        return new String[]{"ui"};
    }

    @Override
    public String getToolBarTitle() {
        return "EditText";
    }

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiFragmentMultiEditTextBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        multiTextView = ((UiFragmentMultiEditTextBinding)binding).uiFragmentMultiEditTextView;
        multiTextView1 = ((UiFragmentMultiEditTextBinding)binding).uiFragmentMultiEditTextView1;
        multiTextView2 = ((UiFragmentMultiEditTextBinding)binding).uiFragmentMultiEditTextView2;
        multiTextView3 = ((UiFragmentMultiEditTextBinding)binding).uiFragmentMultiEditTextView3;
        countLimitEt = ((UiFragmentMultiEditTextBinding)binding).uiFragmentCountLimitEt;

        initEditText();
    }

    private void initEditText(){
        multiTextView.setText("CLASSIC");
        multiTextView.setTextEffect(MultiEditText.TextEffect.CLASSIC);

        multiTextView1.setText("NEON");
        multiTextView1.setTextEffect(MultiEditText.TextEffect.NEON);

        multiTextView2.setText("STROKE");
        multiTextView2.setTextEffect(MultiEditText.TextEffect.STROKE);

        multiTextView3.setText("BOLD");
        multiTextView3.setTextEffect(MultiEditText.TextEffect.BOLD);

        countLimitEt.setCountLimit(4);
    }
}
