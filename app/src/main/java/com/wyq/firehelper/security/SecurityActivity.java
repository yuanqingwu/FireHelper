package com.wyq.firehelper.security;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.base.BaseRecyclerViewActivity;
import com.wyq.firehelper.base.adapter.TvRecyclerViewAdapter;
import com.wyq.firehelper.security.encryption.EncryptActivity;

import java.util.List;

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
                switch (position) {
                    case 0:
                        EncryptActivity.instance(SecurityActivity.this);
                        break;
                    default:
                        break;
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
