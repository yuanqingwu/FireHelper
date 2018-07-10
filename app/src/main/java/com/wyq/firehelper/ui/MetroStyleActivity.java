package com.wyq.firehelper.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.wyq.firehelper.R;
import com.wyq.firehelper.activity.BaseActivity;
import com.wyq.firehelper.ui.widget.MetroImageView;


public class MetroStyleActivity extends BaseActivity {

    private MetroImageView metro_browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.metrolayout);

        metro_browser = (MetroImageView) findViewById(R.id.metro_browser);
        metro_browser.setOnClickIntent(new MetroImageView.OnViewClickListener() {

            @Override
            public void onViewClick(MetroImageView view) {
                // TODO Auto-generated method stub
                Toast.makeText(MetroStyleActivity.this, "网页",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MetroStyleActivity.this,
                        MyWebViewActivity.class));
            }
        });

    }
}






