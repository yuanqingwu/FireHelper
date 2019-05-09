package com.wyq.firehelper.ui.widget.edittext;

import android.view.View;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.R2;
import com.wyq.firehelper.ui.widget.edittext.limitcountedittext.CountEditTextLayout;
import com.wyq.firehelper.ui.widget.edittext.multiedittext.MultiEditText;
import com.wyq.firehelper.ui.widget.edittext.multiedittext.MultiTextView;

import butterknife.BindView;

/**
 * @author yuanqingwu
 * @date 2019/05/09
 */
public class EditTextFragment extends BaseCaseFragment {

    @BindView(R2.id.ui_fragment_multi_edit_text_view)
    public MultiTextView multiTextView;
    @BindView(R2.id.ui_fragment_multi_edit_text_view1)
    public MultiTextView multiTextView1;
    @BindView(R2.id.ui_fragment_multi_edit_text_view2)
    public MultiTextView multiTextView2;
    @BindView(R2.id.ui_fragment_multi_edit_text_view3)
    public MultiTextView multiTextView3;

    @BindView(R2.id.ui_fragment_count_limit_et)
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
    protected int attachLayoutRes() {
        return R.layout.ui_fragment_multi_edit_text;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {


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
