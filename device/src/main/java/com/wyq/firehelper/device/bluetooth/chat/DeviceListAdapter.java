package com.wyq.firehelper.device.bluetooth.chat;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Uni.W
 * @date 2019/3/22 16:56
 */
public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.DeviceViewHolder> {


    public DeviceListAdapter (){

    }

    public void refresh(List<DeviceEntity> entities){

    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder{

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
