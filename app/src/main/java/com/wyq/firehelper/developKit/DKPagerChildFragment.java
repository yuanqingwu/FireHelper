package com.wyq.firehelper.developKit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wyq.firehelper.R;
import com.wyq.firehelper.ui.layout.placeholderview.data.KitInfo;

import java.util.List;

import butterknife.BindView;

public class DKPagerChildFragment extends DevelopKitBaseFragment {

    @BindView(R.id.developkit_fragment_child_recycler_view)
    public RecyclerView recyclerView;

    private DKRecyclerViewAdapter adapter;

    private List<KitInfo> infoList;

    @Override
    public int attachLayoutRes() {
        return R.layout.developkit_fragment_child_layout;
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int position = bundle.getInt("KitInfos");
            infoList = getKits().get(position).getKitInfos();
        }
    }

    @Override
    public void initView() {
        adapter = new DKRecyclerViewAdapter(getContext());
        adapter.setData(infoList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DKRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                startActivityDynamic(getActivity(), infoList.get(position).getName());
            }
        });
    }

    private void startActivityDynamic(Context context, String kitName) {
        String name = "com.wyq.firehelper.developKit." + kitName + "." + kitName + "Activity";
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
