package com.wyq.firehelper.ui.android.chip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.databinding.UiFragmentAndroidChipBinding;

/**
 * @author yuanqingwu
 * @date 2019/04/17
 */
public class ChipFragment extends BaseCaseFragment {

    public ChipGroup chipGroup;

    @Override
    public String[] getArticleFilters() {
        return null;
    }

    @Override
    public String getToolBarTitle() {
        return "Chip";
    }

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiFragmentAndroidChipBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        chipGroup = ((UiFragmentAndroidChipBinding)binding).uiFragmentAndroidChipChipGroup;
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
