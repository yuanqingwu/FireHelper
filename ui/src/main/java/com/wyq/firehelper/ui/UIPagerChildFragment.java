package com.wyq.firehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wyq.firehelper.article.base.CaseActivity;
import com.wyq.firehelper.base.BaseFragment;
import com.wyq.firehelper.base.utils.FireHelperUtils;
import com.wyq.firehelper.ui.databinding.UiListFragmentChildLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class UIPagerChildFragment extends BaseFragment {

    private List<UiInfoBean> uiInfoBeans;
    private String uiType;

    public RecyclerView recyclerView;

    @Override
    protected ViewBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return UiListFragmentChildLayoutBinding.inflate(inflater,container,false);
    }

    @Override
    public void initData() {
        int position = getArguments().getInt("uiTypePosition");
        uiInfoBeans = getUiName().get(position).getInfo();
        uiType = getUiName().get(position).getCategory();
    }

    @Override
    public void initView(View view) {
        recyclerView = ((UiListFragmentChildLayoutBinding)binding).listFragmentChildRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        UIRecyclerViewAdapter adapter = new UIRecyclerViewAdapter(getContext());
        adapter.setData(uiInfoBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new UIRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
//                startActivityDynamic(getContext(), uiType, uiInfoBeans.get(position).getName());
                String name = uiInfoBeans.get(position).getName();
                String pathBase = "com.wyq.firehelper.ui." + uiType.toLowerCase() + "." + name.toLowerCase() + "." + name;

                //模块界面容器允许是fragment或者activity，优先查找fragment
                String pathFragment = pathBase + "Fragment";
                try {
                    Class.forName(pathFragment);
                    CaseActivity.instance(getContext(), pathFragment);
                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
                    String pathActivity = pathBase + "Activity";
                    try {
                        Class.forName(pathActivity);
                        startActivityDynamic(getContext(), pathActivity);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                        Toast.makeText(getContext(), "找不到相关界面，先浏览其他的吧~", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private List<UiBean> getUiName() {
        List<UiBean> uiBeanList = new ArrayList<>();

        String uiJson = FireHelperUtils.readAssets2String(getContext(), "ui.json");
        JsonArray jsonArray = new Gson().fromJson(uiJson, JsonArray.class);
        for (int i = 0; i < jsonArray.size(); i++) {
            UiBean uiBean = new Gson().fromJson(jsonArray.get(i), UiBean.class);
            uiBeanList.add(uiBean);
        }
        return uiBeanList;
    }

    private void startActivityDynamic(Context context, String path) {
//        String name = "com.wyq.firehelper.ui." + category.toLowerCase() + "." + uiName.toLowerCase() + "." + uiName + "Activity";
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName(context,name));
//        context.startActivity(intent);

        try {
            Class clazz = Class.forName(path);
//            Logger.i(clazz.toString());
            context.startActivity(new Intent(context, clazz));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
