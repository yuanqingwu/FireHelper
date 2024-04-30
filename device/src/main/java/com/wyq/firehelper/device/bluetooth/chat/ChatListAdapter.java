package com.wyq.firehelper.device.bluetooth.chat;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.wyq.firehelper.device.R;

import java.util.List;


public class ChatListAdapter extends BaseAdapter {

    private final List<BtMessage> list;
    private final Context context;

    public ChatListAdapter(Context context, List<BtMessage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.bt_chat_item, parent, false);
            holder.textView = convertView.findViewById(R.id.bt_chat_message_text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.textView.setText(list.get(position).getContent());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.addRule(list.get(position).getUserAdress().equals( BluetoothAdapter.getDefaultAdapter().getAddress()) ? RelativeLayout.ALIGN_PARENT_RIGHT
                : RelativeLayout.ALIGN_PARENT_LEFT);
        holder.textView.setLayoutParams(params);

        return convertView;
    }

    private class Holder {
        private TextView textView;
    }
}
