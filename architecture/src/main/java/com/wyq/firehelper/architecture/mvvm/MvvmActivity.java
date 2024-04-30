package com.wyq.firehelper.architecture.mvvm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wyq.firehelper.architecture.databinding.ArchitectureActivityMvvmLayoutBinding;

import java.lang.ref.WeakReference;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class MvvmActivity extends SwipeBackActivity {

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArchitectureActivityMvvmLayoutBinding binding = ArchitectureActivityMvvmLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //替换ButterKnife
        TextView textView = binding.mvvmActivityTv;
        binding.mvvmActivityEt.clearFocus();
        binding.mvvmActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.mvvmActivityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.mvvmActivityRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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

        private final WeakReference<IBackGroundRunable> reference;

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
