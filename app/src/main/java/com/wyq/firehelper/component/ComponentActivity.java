package com.wyq.firehelper.component;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.article.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.article.base.CaseActivity;
import com.wyq.firehelper.component.share.ShareFragment;

import java.util.List;

public class ComponentActivity extends BaseRecyclerViewActivity {

    public static void instance(Context context) {
        context.startActivity(new Intent(context, ComponentActivity.class));
    }

    @Override
    public String[] listItemsNames() {
        return new String[]{"Share"};
    }

    @Override
    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    CaseActivity.instance(ComponentActivity.this, ShareFragment.class.getName());
                }
            }
        };
    }

    @Override
    public String getToolBarTitle() {
        return "Component";
    }

    @Override
    public List getArticleList() {
        return null;
    }
}
