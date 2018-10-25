package com.wyq.firehelper.architecture;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.wyq.firehelper.architecture.mvp.translation.view.MvpActivity;
import com.wyq.firehelper.architecture.mvvm.MvvmActivity;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.java.aop.AopActivity;

import java.util.List;

public class ArchitectureActivity extends BaseRecyclerViewActivity {

    @Override
    public String[] listItemsNames() {
        return new String[]{"MVP[翻译]","MVVM","AOP"};
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
                    case 2:
                        startActivity(AopActivity.class);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public static void instance(Context context) {
        context.startActivity(new Intent(context, ArchitectureActivity.class));
    }

    @Override
    public String getToolBarTitle() {
        return "Architecture";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("Architecture","架构");
    }
}
