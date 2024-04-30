package com.wyq.firehelper.ui.android.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.android.recyclerview.menuchooser.MenuChooserFragment;
import com.wyq.firehelper.ui.android.recyclerview.snaphelper.SnapHelperFragment;
import com.wyq.firehelper.ui.databinding.UiFragmentRecyclerViewBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewbinding.ViewBinding;

/**
 * @author yuanqingwu
 */
public class RecyclerViewFragment extends BaseCaseFragment {

    public Button menuChooserBt;
    public Button snapHelperBt;

    @Override
    public String[] getArticleFilters() {
        return new String[]{"RecyclerView"};
    }

    @Override
    public String getToolBarTitle() {
        return "RecyclerView";
    }

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiFragmentRecyclerViewBinding.inflate(inflater,container,false);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

        menuChooserBt = ((UiFragmentRecyclerViewBinding)binding).uiFragmentRecyclerViewMenuChooserBt;
        snapHelperBt = ((UiFragmentRecyclerViewBinding)binding).uiFragmentRecyclerViewSnapHelperBt;

        menuChooserBt.setOnClickListener(v -> fragmentSelect(new MenuChooserFragment(), "menu_chooser"));
        snapHelperBt.setOnClickListener(v -> fragmentSelect(new SnapHelperFragment(), "snap_helper"));
    }
    private void fragmentSelect(Fragment fragment, String tag) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.ui_fragment_recycler_view_layout, fragment, tag);
        transaction.commit();
    }

}
