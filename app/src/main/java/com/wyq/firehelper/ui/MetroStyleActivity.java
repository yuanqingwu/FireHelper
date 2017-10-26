package com.wyq.firehelper.ui;


import com.wyq.firehelper.R;
import com.wyq.firehelper.widget.MetroImageView;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;


public class MetroStyleActivity extends Activity {

    private MetroImageView metro_browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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






