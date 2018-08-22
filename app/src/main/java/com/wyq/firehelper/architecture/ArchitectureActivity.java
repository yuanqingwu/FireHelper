package com.wyq.firehelper.architecture;

import android.view.View;
import android.widget.AdapterView;

import com.wyq.firehelper.architecture.mvp.translation.view.MvpActivity;
import com.wyq.firehelper.architecture.mvvm.MvvmActivity;
import com.wyq.firehelper.base.BaseListActivity;

public class ArchitectureActivity extends BaseListActivity {

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
    public AdapterView.OnItemClickListener onListItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
