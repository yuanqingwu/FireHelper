package com.wyq.firehelper.ui.android.drawable;

import android.graphics.Color;
import android.widget.ImageView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleConstants;
import com.wyq.firehelper.base.BaseCaseActivity;

import java.util.List;

import butterknife.BindView;

public class DrawableActivity extends BaseCaseActivity {

    @BindView(R.id.ui_activity_drawable_iv)
    public ImageView imageView;

    @Override
    public String toolBarName() {
        return "Drawable";
    }

    @Override
    public List getArticleList() {
        return ArticleConstants.getListByFilter("Drawable");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_drawable;
    }

    @Override
    public void initView() {
        imageView.setImageDrawable(new CircularDrawable(Color.CYAN));
    }

}
