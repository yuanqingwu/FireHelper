package com.wyq.firehelper.base.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wyq.firehelper.R;
import com.wyq.firehelper.article.entity.ArticleResource;
import com.wyq.firehelper.ui.android.recyclerview.itemtouchhelper.ItemTouchHelperAdapter;
import com.wyq.firehelper.ui.android.recyclerview.itemtouchhelper.ItemTouchHelperViewHolder;

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

    private Context context;
    private int selectPosition = -1;
    private int selectedTextColor = -1;
    private LinearLayout.LayoutParams layoutParams;
    private int padding;
    private int gravity;

    private final static int TYPE_TITLE = 1;
    private final static int TYPE_CONTENT = 2;
    private boolean isViewTypeEnable = false;

    public TvRecyclerViewAdapter(List oriList) {
        list.clear();
        list.addAll(oriList);
    }

    public void refreshData(List oriList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return list.size();
            }

            @Override
            public int getNewListSize() {
                return oriList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return list.get(oldItemPosition).getClass().equals(oriList.get(newItemPosition).getClass());
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return list.get(oldItemPosition).equals(oriList.get(newItemPosition));
            }
        }, true);
        list.clear();
        list.addAll(oriList);
        diffResult.dispatchUpdatesTo(this);
//        notifyDataSetChanged();
    }

    public void setSelectPosition(int position, int... selectedTextColor) {
        this.selectPosition = position;
        if (selectedTextColor != null && selectedTextColor.length == 1) {
            this.selectedTextColor = selectedTextColor[0];
        }
        notifyDataSetChanged();
    }

    public void setTvLayoutParam(LinearLayout.LayoutParams param) {
        layoutParams = param;
    }

    public void setTvPadding(int padding) {
        this.padding = padding;
    }

    public void setTvGravity(int gravity) {
        this.gravity = gravity;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutId = (isViewTypeEnable && viewType == TYPE_TITLE) ? R.layout.recyclerview_item_title : R.layout.recyclerview_item_tv_layout;
        TvViewHolder holder = new TvViewHolder(viewType, LayoutInflater.from(context).inflate(layoutId, parent, false));
        return holder;
    }

    public void enableViewType() {
        isViewTypeEnable = true;
    }

    @Override
    public int getItemViewType(int position) {
        //fixme 匹配ui_recyclerview例子
        if (position % 10 == 0) {
            return TYPE_TITLE;
        }
        return TYPE_CONTENT;
    }

    @Override
    public void onBindViewHolder(@NonNull final TvViewHolder holder, final int position) {
        if (list.get(position) instanceof ArticleResource) {
            if (layoutParams != null)
                ((LinearLayout) holder.textView.getParent()).setLayoutParams(layoutParams);
            holder.textView.setPadding(padding, padding, padding, padding);
            if (gravity != 0)
                holder.textView.setGravity(gravity);
            holder.textView.setText(((ArticleResource) list.get(position)).getTitle());
        } else if (list.get(position) instanceof String) {
            if (position == selectPosition) {
                holder.textView.setTextColor(selectedTextColor > 0 ? selectedTextColor : context.getResources().getColor(R.color.colorPrimary));
            } else {
                holder.textView.setTextColor(Color.BLACK);
            }
            holder.textView.setText(list.get(position).toString());
        } else {
            holder.textView.setText(list.get(position).toString());
        }
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
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

        public TvViewHolder(int viewType, View itemView) {
            super(itemView);
            if (isViewTypeEnable && viewType == TYPE_TITLE)
                textView = itemView.findViewById(R.id.recyclerview_item_title_tv);
            else
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
