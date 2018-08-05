package com.wyq.firehelper.developKit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wyq.firehelper.ui.layout.placeholderview.data.DevelopKit;

import java.util.List;

public class DKViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<DevelopKit> kitList = null;
    private Context context;

    public DKViewPagerAdapter(FragmentManager fm, Context context, List<DevelopKit> kitList) {
        super(fm);
        this.context = context;
        this.kitList = kitList;
    }

    @Override
    public Fragment getItem(int position) {
//        String name = kitList.get(position).getKitInfos().get(0).getName();
//        String className = "com.wyq.firehelper.developKit." + name + "." + name + "Fragment";
//        try {
//            Class clazz = Class.forName(className,true,context.getClassLoader());
//            return (Fragment) clazz.newInstance();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }

        DKPagerChildFragment fragment = new DKPagerChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("KitInfos",position);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public int getCount() {
        return kitList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return kitList.get(position).getCategory();
    }
}
