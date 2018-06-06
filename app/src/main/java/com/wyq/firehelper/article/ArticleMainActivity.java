package com.wyq.firehelper.article;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wyq.firehelper.R;

import java.util.List;

/**
 * Created by Uni.W on 2017/10/26.
 */

public class ArticleMainActivity extends Activity implements View.OnClickListener {

    private TextView urlTv;
    private RecyclerView recyclerView ;

//    private Map<String, List<String>> urlList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_main);

        initView();

    }

    private void initView() {
        urlTv = (TextView) findViewById(R.id.activity_article_url_tv);
        recyclerView = (RecyclerView)findViewById(R.id.activity_article_recyclerview);

//        urlTv.setText(ArticleConstants._1.getTitle());
        urlTv.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        final List<ArticleResource> resourceList = ArticleConstants.getAllFiled();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(resourceList);
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("url",resourceList.get(position).getUrl());
                intent.setClass(ArticleMainActivity.this,WebViewActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        RichText.recycle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_article_url_tv:

                break;
                default:
                    break;
        }
    }
}
