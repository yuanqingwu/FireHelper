package com.wyq.firehelper.developkit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DKRecyclerViewAdapter extends RecyclerView.Adapter<DKRecyclerViewAdapter.DKViewHolder> {

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    private final List<KitInfo> infoList = new ArrayList<>();
    private final LayoutInflater inflater;

    public DKRecyclerViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<KitInfo> kitInfos){
        infoList.clear();
        infoList.addAll(kitInfos);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DKViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.developkit_activity_main_recyclerview_item,parent,false);
        DKViewHolder holder = new DKViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DKViewHolder holder, final int position) {
        holder.titleTV.setText(infoList.get(position).getName());
        holder.descTv.setText(infoList.get(position).getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class DKViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleTV;
        private final TextView descTv;

        public DKViewHolder(View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.activity_developkit_main_recycler_view_title);
            descTv = itemView.findViewById(R.id.activity_developkit_main_recycler_view_desc);
        }
    }
}
