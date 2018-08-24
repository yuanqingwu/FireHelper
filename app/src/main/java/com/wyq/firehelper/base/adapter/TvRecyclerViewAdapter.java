package com.wyq.firehelper.base.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.ArticleResource;
import com.wyq.firehelper.ui.layout.recyclerView.ItemTouchHelperAdapter;
import com.wyq.firehelper.ui.layout.recyclerView.ItemTouchHelperViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TvRecyclerViewAdapter extends RecyclerView.Adapter<TvRecyclerViewAdapter.TvViewHolder> implements ItemTouchHelperAdapter {

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


    private List list = new ArrayList<>();

    public TvRecyclerViewAdapter(List oriList) {
        list.clear();
        list.addAll(oriList);
    }

    public void refreshData(List oriList) {
        list.clear();
        list.addAll(oriList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TvViewHolder holder = new TvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_tv_layout, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TvViewHolder holder, final int position) {
        if (list.get(position) instanceof ArticleResource) {
            holder.textView.setText(((ArticleResource) list.get(position)).getTitle());
        } else if (list.get(position) instanceof String) {
            holder.textView.setText(list.get(position).toString());
        }
        if (onItemClickListener != null) {
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.textView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        public TextView textView;

        public TvViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerview_item_text);
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
