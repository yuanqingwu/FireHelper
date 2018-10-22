package com.wyq.firehelper.framework;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.wyq.firehelper.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.framework.service.ServiceActivity;

public class FrameworkActivity extends BaseRecyclerViewActivity {


    @Override
    public String[] listItemsNames() {
        return new String[]{"Service", "Broadcast", "Thread"};
    }

    @Override
    public String toolBarName() {
        return "Framework";
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
                        ServiceActivity.instance(FrameworkActivity.this);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public static void instance(Context context){
        context.startActivity(new Intent(context,FrameworkActivity.class));
    }
}
