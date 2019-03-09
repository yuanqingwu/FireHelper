package com.wyq.firehelper.media.video;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.media.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author uni.w
 * @date 2018/11/27 16:28
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<Uri> uriList = null;

    public VideoRecyclerViewAdapter(List<Uri> urls){
        uriList = urls;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.media_video_fragment_recycler_view_item_layout,parent,false);
        VideoViewHolder videoViewHolder = new VideoViewHolder(view);
        return videoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VideoViewHolder){
//            FirePlayerView firePlayerView = ((VideoViewHolder) holder).videoControllerView.getFirePlayerView(uriList.get(position));
            FirePlayerView firePlayerView = ((VideoViewHolder) holder).firePlayerView.setVideoURI(uriList.get(position));
            boolean isVisible = firePlayerView.isVisible(true);
            Logger.i(position +" isVisible:"+isVisible);
            if(isVisible){
                firePlayerView.autoPlay(true).openVideo(true);
            }else{
                firePlayerView.pause();
            }
        }
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }

    protected class VideoViewHolder extends RecyclerView.ViewHolder {

//        private FireVideoControllerView videoControllerView;
        private FirePlayerView firePlayerView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            firePlayerView = itemView.findViewById(R.id.recyclerview_item_fire_player_view);
        }

    }
}
