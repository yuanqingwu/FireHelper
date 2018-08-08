package com.wyq.firehelper.developKit.Glide;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.developKit.DevelopKitBaseActivity;

import butterknife.BindView;

public class GlideActivity extends DevelopKitBaseActivity {

    @BindView(R.id.activity_developkit_glide_img_1)
    public ImageView imageView;
    @BindView(R.id.activity_developkit_glide_tv)
    public TextView textView;

    @Override
    public void initData() {
        resourceList.put(textView, ArticleConstants.DEVKIT_IMAGE_GLIDE_0);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.developkit_activity_glide_layout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        browserArticle(GlideActivity.this);
        Glide.with(this).load("https://github.com/bumptech/glide/raw/master/static/glide_logo.png")
                .into(imageView);
    }
}
