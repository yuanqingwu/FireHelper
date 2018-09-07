package com.wyq.firehelper.developKit;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.wyq.firehelper.R;

import butterknife.BindView;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class DevelopKitMainActivity extends DevelopKitBaseActivity {
    @BindView(R.id.developkit_activity_main_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.developkit_activity_main_tablayout)
    public TabLayout tabLayout;
    @BindView(R.id.developkit_activity_main_viewpager)
    public ViewPager viewPager;

    @Override
    public void initData() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.developkit_activity_main_layout;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "DevelopKit", true);
    }

    @Override
    public void initView() {
        super.initView();
        for (DevelopKit kit : getKits()) {
            tabLayout.addTab(tabLayout.newTab().setText(kit.getCategory()));
        }

        DKViewPagerAdapter adapter = new DKViewPagerAdapter(getSupportFragmentManager(), this, getKits());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
