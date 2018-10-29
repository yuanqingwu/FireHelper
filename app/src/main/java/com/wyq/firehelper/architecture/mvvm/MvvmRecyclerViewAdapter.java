package com.wyq.firehelper.architecture.mvvm;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wyq.firehelper.databinding.MvvmRecyclerItemBinding;

import java.util.List;

public class MvvmRecyclerViewAdapter extends RecyclerView.Adapter<MvvmRecyclerViewAdapter.MvvmViewHolder> {

    private List<NameModel> nameModels;

    public MvvmRecyclerViewAdapter(List<NameModel> nameModels) {
        this.nameModels = nameModels;
    }

    @NonNull
    @Override
    public MvvmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MvvmRecyclerItemBinding itemBinding = MvvmRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MvvmViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MvvmViewHolder holder, int position) {
        holder.bind(nameModels.get(position));
    }

    @Override
    public int getItemCount() {
        if (nameModels == null)
            return 0;
        return nameModels.size();
    }

    public class MvvmViewHolder extends RecyclerView.ViewHolder{

        private MvvmRecyclerItemBinding itemBinding;

        public MvvmViewHolder(MvvmRecyclerItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(NameModel model){
            itemBinding.setName(model);
            itemBinding.executePendingBindings();
        }
    }
}
