package com.wyq.firehelper.architecture;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wyq.firehelper.architecture.mvvm.MvvmActivity;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.article.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.navigation.NavigationManager;

import java.util.List;

@Route(path = NavigationManager.NAVIGATION_ARCHITECTURE_MAIN_ACTIVITY)
public class ArchitectureActivity extends BaseRecyclerViewActivity {

    @Override
    public String[] listItemsNames() {
        return new String[]{"MVVM","AOP"};
    }

    @Override
    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        startActivity(MvvmActivity.class);
                        break;
                    case 1:
//                        startActivity(AopActivity.class);
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
