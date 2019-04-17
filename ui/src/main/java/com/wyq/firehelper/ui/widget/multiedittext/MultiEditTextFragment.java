package com.wyq.firehelper.ui.widget.multiedittext;

import android.view.View;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R2;
import com.wyq.firehelper.ui.R;

import butterknife.BindView;

public class MultiEditTextFragment extends BaseCaseFragment {

    @BindView(R2.id.ui_fragment_multi_edit_text_view)
    public MultiTextView multiTextView;
    @BindView(R2.id.ui_fragment_multi_edit_text_view1)
    public MultiTextView multiTextView1;
    @BindView(R2.id.ui_fragment_multi_edit_text_view2)
    public MultiTextView multiTextView2;
    @BindView(R2.id.ui_fragment_multi_edit_text_view3)
    public MultiTextView multiTextView3;


    @Override
    public String[] getArticleFilters() {
        return new String[]{"MultiEditText"};
    }

    @Override
    public String getToolBarTitle() {
        return "MultiEditText";
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
        multiTextView.setText("CLASSIC");
        multiTextView.setTextEffect(MultiEditText.TextEffect.CLASSIC);

        multiTextView1.setText("NEON");
        multiTextView1.setTextEffect(MultiEditText.TextEffect.NEON);

        multiTextView2.setText("STROKE");
        multiTextView2.setTextEffect(MultiEditText.TextEffect.STROKE);

        multiTextView3.setText("BOLD");
        multiTextView3.setTextEffect(MultiEditText.TextEffect.BOLD);
    }
}
