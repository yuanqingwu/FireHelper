package com.wyq.firehelper.ui.android.chip;

import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.R2;

import butterknife.BindView;

/**
 * @author yuanqingwu
 * @date 2019/04/17
 */
public class ChipFragment extends BaseCaseFragment {

    @BindView(R2.id.ui_fragment_android_chip_chip_group)
    public ChipGroup chipGroup;
    @BindView(R2.id.ui_fragment_android_chip_chip)
    public Chip chip;

    @Override
    public String[] getArticleFilters() {
        return null;
    }

    @Override
    public String getToolBarTitle() {
        return "Chip";
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_fragment_android_chip;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        chipGroup.setChipSpacing(16);
        for (int i = 0; i < 20; i++) {
            Chip chip = new Chip(getContext());
            chip.setText("chip "+i);
            chip.setCheckable(true);
            ChipGroup.LayoutParams params = new ChipGroup.LayoutParams(ChipGroup.LayoutParams.WRAP_CONTENT,ChipGroup.LayoutParams.WRAP_CONTENT);
//            params.topMargin = 16;
//            params.leftMargin = 16;
//            params.rightMargin = 16;
            chipGroup.addView(chip,params);
        }
    }
}
