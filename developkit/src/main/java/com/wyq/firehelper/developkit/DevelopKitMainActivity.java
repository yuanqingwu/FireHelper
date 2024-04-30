package com.wyq.firehelper.developkit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.base.navigation.NavigationManager;
import com.wyq.firehelper.base.utils.FireHelperUtils;
import com.wyq.firehelper.developkit.databinding.DevelopkitActivityMainLayoutBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Uni.W on 2016/8/10.
 */
@Route(path = NavigationManager.NAVIGATION_DEVELOP_KIT_MAIN_ACTIVITY)
public class DevelopKitMainActivity extends BaseActivity {

    public Toolbar toolbar;
    public TabLayout tabLayout;
    public ViewPager viewPager;

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DevelopkitActivityMainLayoutBinding.inflate(layoutInflater);
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "DevelopKit", true);
    }

    @Override
    public void initView() {
        toolbar = ((DevelopkitActivityMainLayoutBinding)viewBinding).developkitActivityMainToolbar;
        tabLayout = ((DevelopkitActivityMainLayoutBinding)viewBinding).developkitActivityMainTablayout;
        viewPager = ((DevelopkitActivityMainLayoutBinding)viewBinding).developkitActivityMainViewpager;
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
