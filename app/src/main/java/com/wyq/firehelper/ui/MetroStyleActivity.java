package com.wyq.firehelper.ui;


import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.ui.widget.MetroImageView;


public class MetroStyleActivity extends BaseActivity {

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
//                startActivity(new Intent(MetroStyleActivity.this,
//                        WebViewActivity.class));
            }
        });

    }
}






