package com.wyq.firehelper.developKit.Glide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.article.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GlideActivity extends AppCompatActivity {

    @BindView(R.id.activity_developkit_glide_img_1)
    public ImageView imageView;
    @BindView(R.id.activity_developkit_glide_tv)
    public TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null)getSupportActionBar().hide();
        setContentView(R.layout.developkit_activity_glide_layout);
        ButterKnife.bind(this);

        textView.setText(ArticleConstants._5_0_1.getTitle());

        Glide.with(this).load("https://github.com/bumptech/glide/raw/master/static/glide_logo.png")
                .into(imageView);

    }


    @OnClick(R.id.activity_developkit_glide_tv)
    public void readArticle() {
        Intent intent = new Intent();
        intent.putExtra("url", ArticleConstants._5_3_0.getUrl());
        intent.setClass(GlideActivity.this, WebViewActivity.class);
        startActivity(intent);
    }
}
