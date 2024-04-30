package com.wyq.firehelper.security;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.article.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.navigation.NavigationManager;
import com.wyq.firehelper.security.encryption.EncryptActivity;

import java.util.List;

@Route(path = NavigationManager.NAVIGATION_SECURITY_MAIN_ACTIVITY)
public class SecurityActivity extends BaseRecyclerViewActivity {


    public static void instance(Context context){
        context.startActivity(new Intent(context,SecurityActivity.class));
    }

    @Override
    public String[] listItemsNames() {
        return new String[]{"Encryption"};
    }

    @Override
    public TvRecyclerViewAdapter.OnItemClickListener onListItemClickListener() {
        return new TvRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    EncryptActivity.instance(SecurityActivity.this);
                }
            }
        };
    }

    @Override
    public String getToolBarTitle() {
        return "Security";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("安全","Security");
    }
}
