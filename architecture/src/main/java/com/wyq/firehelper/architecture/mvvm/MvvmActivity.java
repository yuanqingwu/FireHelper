package com.wyq.firehelper.architecture.mvvm;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyq.firehelper.architecture.R;
import com.wyq.firehelper.architecture.databinding.MvvmBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.OnRebindCallback;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;
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
        binding.mvvmActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
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
                new BackGroundRunable(new IBackGroundRunable() {
                    @Override
                    public void onBackGround() {
                        int i = 0;
                        while (!Thread.currentThread().isInterrupted()) {
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
                })
        );
        thread.start();
    }

    @Override
    protected void onDestroy() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        super.onDestroy();
    }

    public interface IBackGroundRunable {
        void onBackGround();
    }

    public static class BackGroundRunable implements Runnable {

        private WeakReference<IBackGroundRunable> reference;

        public BackGroundRunable(IBackGroundRunable runable) {
            reference = new WeakReference<>(runable);
        }

        @Override
        public void run() {
            if (reference != null) {
                reference.get().onBackGround();
            }
        }
    }
}
