package com.wyq.firehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.media.opengles.OpenGLESActivity;
import com.wyq.firehelper.utils.FireHelperUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UiMainActivity extends BaseActivity {

    @BindView(R.id.ui_activity_main_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.ui_activity_main_viewpager)
    public ViewPager viewPager;
    @BindView(R.id.ui_activity_main_tablayout)
    public TabLayout tabLayout;

    @Override
    protected int attachLayoutRes() {
        return R.layout.ui_activity_main_layout;
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "UI", true);
    }

    @Override
    public void initView() {
        for (UiBean type : getUiName()) {
            tabLayout.addTab(tabLayout.newTab().setText(type.getCategory()));
        }

        UiViewPagerAdapter pagerAdapter = new UiViewPagerAdapter(getSupportFragmentManager(), getUiName());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private List<UiBean> getUiName() {
        List<UiBean> uiBeanList = new ArrayList<>();

        String uiJson = FireHelperUtils.readAssets2String(this, "ui.json");
        JsonArray jsonArray = new Gson().fromJson(uiJson, JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            UiBean uiBean = new Gson().fromJson(jsonArray.get(i), UiBean.class);
            uiBeanList.add(uiBean);
        }
        return uiBeanList;
    }

    public static void instance(Context context) {
        context.startActivity(new Intent(context, UiMainActivity.class));
    }
}
