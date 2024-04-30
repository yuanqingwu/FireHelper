package com.wyq.firehelper.ui.android.draglayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.databinding.UiLayoutDragLayoutBinding;

public class DragLayoutFragment extends BaseCaseFragment {
    @Override
    public String[] getArticleFilters() {
        return new String[]{"DragLayout"};
    }

    @Override
    public String getToolBarTitle() {
        return "DragLayout";
    }
    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiLayoutDragLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }
}
