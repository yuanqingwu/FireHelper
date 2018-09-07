package com.wyq.firehelper.developKit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyq.firehelper.R;

import java.util.ArrayList;
import java.util.List;

public class DKRecyclerViewAdapter extends RecyclerView.Adapter<DKRecyclerViewAdapter.DKViewHolder> {

    public interface OnItemClickListener{
        void onClick(View view,int position);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    private List<KitInfo> infoList = new ArrayList<>();
    private LayoutInflater inflater;

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

        private TextView titleTV,descTv;

        public DKViewHolder(View itemView) {
            super(itemView);
            titleTV = (TextView) itemView.findViewById(R.id.activity_developkit_main_recycler_view_title);
            descTv = (TextView)itemView.findViewById(R.id.activity_developkit_main_recycler_view_desc);
        }
    }
}
