package com.wyq.firehelper.developkit.glide;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.base.BaseCaseActivity;

import java.util.List;

import butterknife.BindView;

public class GlideActivity extends BaseCaseActivity {

    @BindView(R.id.activity_developkit_glide_img_1)
    public ImageView imageView;
    @BindView(R.id.activity_developkit_glide_img_2)
    public ImageView imageView2;
    @BindView(R.id.activity_developkit_glide_tv)
    public TextView textView;

    private String EXAMPLE_URL = "https://github.com/bumptech/glide/raw/master/static/glide_logo.png";


    @Override
    protected int attachLayoutRes() {
        return R.layout.developkit_activity_glide_layout;
    }

    @Override
    public String toolBarName() {
        return "Glide";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("Glide");
    }

    @Override
    public void initView() {

        GlideApp.with(this).load(EXAMPLE_URL).placeholder(R.drawable.ic_image_place_holder_128px).fitCenter().into(imageView);

        Glide.with(this).load(EXAMPLE_URL).into(imageView2);

    }
}
