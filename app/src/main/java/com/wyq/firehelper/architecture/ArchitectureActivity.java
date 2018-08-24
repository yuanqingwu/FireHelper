package com.wyq.firehelper.architecture;

import android.view.View;

import com.wyq.firehelper.architecture.mvp.translation.view.MvpActivity;
import com.wyq.firehelper.architecture.mvvm.MvvmActivity;
import com.wyq.firehelper.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;

public class ArchitectureActivity extends BaseRecyclerViewActivity {

    @Override
    public String[] listItemsNames() {
        return new String[]{"MVP[翻译]","MVVM"};
    }

    @Override
    public String toolBarName() {
        return "architecture";
    }

    @Override
    public boolean isShowBackIcon() {
        return true;
    }

    @Override
    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(MvpActivity.class);
                        break;
                    case 1:
                        startActivity(MvvmActivity.class);
                        break;
                    default:
                        break;
                }
            }
        };
    }


}
