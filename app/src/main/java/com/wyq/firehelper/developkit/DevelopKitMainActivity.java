package com.wyq.firehelper.developkit;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.utils.FireHelperUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Uni.W on 2016/8/10.
 */
public class DevelopKitMainActivity extends BaseActivity {
    @BindView(R.id.developkit_activity_main_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.developkit_activity_main_tablayout)
    public TabLayout tabLayout;
    @BindView(R.id.developkit_activity_main_viewpager)
    public ViewPager viewPager;

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
        for (DevelopKit kit : getKits()) {
            tabLayout.addTab(tabLayout.newTab().setText(kit.getCategory()));
        }

        DKViewPagerAdapter adapter = new DKViewPagerAdapter(getSupportFragmentManager(), this, getKits());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static void instance(Context context) {
        context.startActivity(new Intent(context, DevelopKitMainActivity.class));
    }

    public List<DevelopKit> getKits(){
        List<DevelopKit> kitsList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONArray kitsArray = new JSONArray(FireHelperUtils.readAssets2String(this,"developKit.json"));
            for(int i=0; i<kitsArray.length();i++){
                DevelopKit kit = gson.fromJson(kitsArray.getString(i),DevelopKit.class);
                kitsList.add(kit);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return kitsList;
    }

}
