package com.wyq.firehelper.article.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyq.firehelper.article.entity.ArticleSaveEntity;
import com.wyq.firehelper.base.FireModule;
import com.wyq.firehelper.base.R;
import com.wyq.firehelper.base.widget.recyclerview.itemtouchhelper.ItemTouchHelperAdapter;
import com.wyq.firehelper.base.widget.recyclerview.itemtouchhelper.ItemTouchHelperViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

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

    public interface OnTvImgItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnTvImgItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    private OnTvImgItemClickListener onTvImgItemClickListener;
    private OnTvImgItemLongClickListener onTvImgItemLongClickListener;

    public void setOnItemClickListener(OnTvImgItemClickListener onItemClickListener) {
        this.onTvImgItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnTvImgItemLongClickListener onItemLongClickListener) {
        this.onTvImgItemLongClickListener = onItemLongClickListener;
    }

    private List<?> list = new ArrayList<>();
    private Context context;
    private int orientation = VERTICAL;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;


    public TvImgRecyclerViewAdapter(Context context, List oriList) {
        this.context = context;
        list.clear();
        list.addAll(oriList);
    }

    public void refreshData(final List oriList) {
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

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = orientation == VERTICAL ? R.layout.recyclerview_item_tv_img_layout_v : R.layout.base_recyclerview_item_tv_img_layout_h;
        TvViewHolder holder = new TvViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, final int position) {

        if (list.get(position) instanceof FireModule) {
            holder.textView.setText(((FireModule) list.get(position)).getTitleZh().toString());
            holder.imageView.setImageBitmap(getBitmapByName(((FireModule) list.get(position)).getHeadImage()));
        } else if (list.get(position) instanceof ArticleSaveEntity) {
            ArticleSaveEntity entity = (ArticleSaveEntity) list.get(position);
            holder.textView.setText(entity.getWebTitle());
            holder.imageView.setImageBitmap(entity.getWebIcon());
        }

        if (onTvImgItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTvImgItemClickListener.onItemClick(v, position);
                }
            });
        }

        if (onTvImgItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onTvImgItemLongClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    private Bitmap getBitmapByName(String name) {
//        Logger.i(name);
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
//            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
//            itemView.setBackgroundColor(0);
        }
    }

}
