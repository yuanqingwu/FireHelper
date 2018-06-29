package com.wyq.firehelper.developKit.Glide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wyq.firehelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GlideActivity extends AppCompatActivity {

    @BindView(R.id.activity_developkit_glide_img_1)
    public ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developkit_glide_layout);
        ButterKnife.bind(this);

        Glide.with(this).load("https://github.com/bumptech/glide/raw/master/static/glide_logo.png")
                .into(imageView);
    }
}
