package com.wyq.firehelper.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.BaseFragment;
import com.wyq.firehelper.utils.FireHelperUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UIPagerChildFragment extends BaseFragment {

    private List<UiInfoBean> uiInfoBeans;
    private String uiType;

    @BindView(R.id.list_fragment_child_recycler_view)
    public RecyclerView recyclerView;

    @Override
    public int attachLayoutRes() {
        return R.layout.list_fragment_child_layout;
    }

    @Override
    public void initData() {
        int position = getArguments().getInt("uiTypePosition");
        uiInfoBeans = getUiName().get(position).getInfo();
        uiType = getUiName().get(position).getCategory();
    }

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        UIRecyclerViewAdapter adapter = new UIRecyclerViewAdapter(getContext());
        adapter.setData(uiInfoBeans);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new UIRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivityDynamic(getContext(), uiType, uiInfoBeans.get(position).getName());
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

    private void startActivityDynamic(Context context, String category, String uiName) {
        String name = "com.wyq.firehelper.ui." + category.toLowerCase() + "." + uiName.toLowerCase() + "." + uiName + "Activity";
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName(context,name));
//        context.startActivity(intent);

        try {
            Class clazz = Class.forName(name);
//            Logger.i(clazz.toString());
            context.startActivity(new Intent(context, clazz));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"找不到相关界面，先浏览其他的吧~",Toast.LENGTH_SHORT).show();
        }
    }


}
