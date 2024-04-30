package com.wyq.firehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.base.navigation.NavigationManager;
import com.wyq.firehelper.base.utils.FireHelperUtils;
import com.wyq.firehelper.ui.databinding.UiActivityMainLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;

@Route(path = NavigationManager.NAVIGATION_UI_MAIN_ACTIVITY)
public class UIMainActivity extends BaseActivity {

    public Toolbar toolbar;
    public ViewPager viewPager;
    public TabLayout tabLayout;


    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return UiActivityMainLayoutBinding.inflate(layoutInflater);
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "UI", true);
    }

    @Override
    public void initView() {
        toolbar = ((UiActivityMainLayoutBinding)viewBinding).uiActivityMainToolbar;
        viewPager = ((UiActivityMainLayoutBinding)viewBinding).uiActivityMainViewpager;
        tabLayout = ((UiActivityMainLayoutBinding)viewBinding).uiActivityMainTablayout;


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
        context.startActivity(new Intent(context, UIMainActivity.class));
    }
}
