package com.wyq.firehelper.ui.widget.metroimageview;


import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;


public class MetroImageViewActivity extends BaseActivity {

    private MetroImageView metro_browser;

    @Override
    protected int attachLayoutRes() {
        return R.layout.metrolayout;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initView() {
        metro_browser = (MetroImageView) findViewById(R.id.metro_browser);
        metro_browser.setOnClickIntent(new MetroImageView.OnViewClickListener() {

            @Override
            public void onViewClick(MetroImageView view) {
//                startActivity(new Intent(MetroImageViewActivity.this,
//                        WebViewActivity.class));
            }
        });

    }
}






