package com.wyq.firehelper.architecture.mvvm;

import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.transition.TransitionManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.databinding.MvvmBinding;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class MvvmActivity extends SwipeBackActivity {

    private MvvmRecyclerViewAdapter adapter;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.architecture_activity_mvvm_layout);

        //替换ButterKnife
        TextView textView = binding.mvvmActivityTv;
        binding.mvvmActivityEt.clearFocus();
        final NameModel nameModel = new NameModel();
        nameModel.name.set("wyq");
        binding.setName(nameModel);
        binding.setImageUrl("https://www.chiphell.com/static/image/common/logo.png");

        final List<NameModel> nameModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NameModel model = new NameModel();
            model.name.set("wyq:" + i);
            nameModels.add(model);
        }
        binding.mvvmActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.mvvmActivityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.mvvmActivityRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new MvvmRecyclerViewAdapter(nameModels);
        binding.mvvmActivityRecyclerView.setAdapter(adapter);

        binding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup viewGroup = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(viewGroup);
                return true;
            }
        });

       thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while (true) {
                            nameModel.name.set(nameModel.name.get() + "q");
                            binding.setIsShow(!binding.getIsShow());
                            i++;
                            if (i > 30) {
                                nameModel.name.set("wyq");
                                i = 0;
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
       thread.start();
    }

    @Override
    protected void onDestroy() {
        if(thread != null){
            thread.interrupt();
            thread = null;
        }
        super.onDestroy();
    }
}
