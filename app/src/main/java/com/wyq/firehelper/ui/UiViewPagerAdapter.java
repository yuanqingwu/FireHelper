package com.wyq.firehelper.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class UiViewPagerAdapter extends FragmentStatePagerAdapter {

    public List<UiBean> uiTypeList;
    public UiViewPagerAdapter(FragmentManager fm, List<UiBean> uiTypeList) {
        super(fm);
        this.uiTypeList = uiTypeList;
    }

    @Override
    public Fragment getItem(int position) {
        UIPagerChildFragment fragment = new UIPagerChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("uiTypePosition",position);
        bundle.putString("uiType",uiTypeList.get(position).getCategory());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return uiTypeList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return uiTypeList.get(position).getCategory();
    }
}
