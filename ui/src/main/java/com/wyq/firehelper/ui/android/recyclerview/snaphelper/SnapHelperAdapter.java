package com.wyq.firehelper.ui.android.recyclerview.snaphelper;

import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wyq.firehelper.ui.R;

import java.util.List;

/**
 * @author yuanqingwu
 * @date 2020/01/16
 */
public class SnapHelperAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private ViewGroup.LayoutParams mLayoutParams;
    private int mBackgroundColor;

    public SnapHelperAdapter(@Nullable List<String> data) {
        super(R.layout.base_textview, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (mLayoutParams != null) {
            helper.getView(R.id.base_text_view).setLayoutParams(mLayoutParams);
        }

        if(mBackgroundColor != 0){
            helper.setBackgroundColor(R.id.base_text_view,mBackgroundColor);
        }

        helper.setText(R.id.base_text_view, item);
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.mLayoutParams = layoutParams;
    }

    public void setBackgroundColor(@ColorInt int color){
        this.mBackgroundColor = color;
    }

}
