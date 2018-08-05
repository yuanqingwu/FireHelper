package com.wyq.firehelper.developKit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.wyq.firehelper.R;
import com.wyq.firehelper.ui.layout.placeholderview.data.DevelopKit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class DevelopKitMainActivity extends DevelopKitBaseActivity {
//    @BindView(R.id.activity_developkit_main_eph_view)
//    public ExpandablePlaceHolderView ephView;

    @BindView(R.id.developkit_activity_main_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.developkit_activity_main_tablayout)
    public TabLayout tabLayout;
    @BindView(R.id.developkit_activity_main_viewpager)
    public ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developkit_activity_main_layout);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView(){
        toolbar.setTitle("DevelopKit");
        initToolbarNav(toolbar);
        setSupportActionBar(toolbar);

        for(DevelopKit kit :getKits()) {
            tabLayout.addTab(tabLayout.newTab().setText(kit.getCategory()));
        }

        DKViewPagerAdapter adapter = new DKViewPagerAdapter(getSupportFragmentManager(),this,getKits());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//
//        DKRecyclerViewAdapter adapter = new DKRecyclerViewAdapter(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        recyclerView.setAdapter(adapter);

//        for(DevelopKit kit : getKits()){
//            ephView.addView(new HeadView(kit.getCategory()));
//            for(KitInfo info : kit.getKitInfos()){
//                ephView.addView(new ItemView(this,info.getName(),info.getDescription()));
//            }
//        }

    }


}
