package com.wyq.firehelper.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UIRecyclerViewAdapter extends RecyclerView.Adapter<UIRecyclerViewAdapter.UIViewHolder> {

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private final List<UiInfoBean> infoList = new ArrayList<>();
    private final LayoutInflater inflater;

    public UIRecyclerViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<UiInfoBean> beans) {
        infoList.clear();
        infoList.addAll(beans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UIViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ui_recyclerview_item, parent, false);
        UIViewHolder holder = new UIViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UIViewHolder holder, final int position) {
        holder.titleTV.setText(infoList.get(position).getName());
        String desc = infoList.get(position).getDescription();
        if(desc == null || desc.isEmpty()){
            holder.descTv.setVisibility(View.GONE);
        }else{
            holder.descTv.setVisibility(View.VISIBLE);
            holder.descTv.setText(desc);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class UIViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTV;
        private final TextView descTv;

        public UIViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.activity_developkit_main_recycler_view_title);
            descTv = itemView.findViewById(R.id.activity_developkit_main_recycler_view_desc);
        }
    }
}
