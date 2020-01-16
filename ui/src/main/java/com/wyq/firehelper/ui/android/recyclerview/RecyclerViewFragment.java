package com.wyq.firehelper.ui.android.recyclerview;

import android.view.View;
import android.widget.Button;

import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.base.BaseCaseFragment;
import com.wyq.firehelper.ui.R;
import com.wyq.firehelper.ui.R2;
import com.wyq.firehelper.ui.android.recyclerview.menuchooser.DetailRVFragment;
import com.wyq.firehelper.ui.android.recyclerview.menuchooser.MenuChooserFragment;
import com.wyq.firehelper.ui.android.recyclerview.menuchooser.SelectListener;
import com.wyq.firehelper.ui.android.recyclerview.snaphelper.SnapHelperFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author yuanqingwu
 */
public class RecyclerViewFragment extends BaseCaseFragment {

    @BindView(R2.id.ui_fragment_recycler_view_menu_chooser_bt)
    public Button menuChooserBt;
    @BindView(R2.id.ui_fragment_recycler_view_snap_helper_bt)
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
    protected int attachLayoutRes() {
        return R.layout.ui_fragment_recycler_view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @OnClick(R2.id.ui_fragment_recycler_view_menu_chooser_bt)
    public void menuChooser() {
        fragmentSelect(new MenuChooserFragment(), "menu_chooser");
    }


    @OnClick(R2.id.ui_fragment_recycler_view_snap_helper_bt)
    public void snapHelper() {
        fragmentSelect(new SnapHelperFragment(), "snap_helper");
    }

    private void fragmentSelect(Fragment fragment, String tag) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.ui_fragment_recycler_view_layout, fragment, tag);
        transaction.commit();
    }

}
