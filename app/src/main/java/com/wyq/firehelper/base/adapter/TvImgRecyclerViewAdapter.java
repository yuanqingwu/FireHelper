package com.wyq.firehelper.base.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.R;
import com.wyq.firehelper.base.FireModule;
import com.wyq.firehelper.ui.layout.recyclerview.itemtouchhelper.ItemTouchHelperAdapter;
import com.wyq.firehelper.ui.layout.recyclerview.itemtouchhelper.ItemTouchHelperViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TvImgRecyclerViewAdapter extends RecyclerView.Adapter<TvImgRecyclerViewAdapter.TvViewHolder> implements ItemTouchHelperAdapter {

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private List<FireModule> list = new ArrayList<>();
    private Context context;

    public TvImgRecyclerViewAdapter(Context context,List<FireModule> oriList) {
        this.context = context;
        list.clear();
        list.addAll(oriList);
    }

    public void refreshData(List<FireModule> oriList) {
        list.clear();
        list.addAll(oriList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TvViewHolder holder = new TvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_tv_img_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).getTitleZh().toString());
        holder.imageView.setImageBitmap(getBitmapByName(list.get(position).getHeadImage()));
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    public Bitmap getBitmapByName(String name) {
        Logger.i(name);
        ApplicationInfo appInfo = context.getApplicationInfo();
        int resID = context.getResources().getIdentifier(name, "drawable", appInfo.packageName);
        return BitmapFactory.decodeResource(context.getResources(), resID);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public TextView textView;
        public ImageView imageView;

        public TvViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerview_item_tv_img_text);
            imageView = itemView.findViewById(R.id.recyclerview_item_tv_img_img);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

}
