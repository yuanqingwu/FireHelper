package com.wyq.firehelper.device.bluetooth.chat;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.device.R;
import com.wyq.firehelper.device.databinding.DevicesActivityBtChatLayoutBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

public class BtChatActivity extends BaseActivity {

    public Toolbar toolbar;
    private ListView chatListView;
    private EditText editText;
    private Button sendButton;

    private String userName;
    public ChatListAdapter adapter;
    // private List<BtMessage> list;
    private BluetoothAdapter bluetoothAdapter;

    private BtChatService btChatService;

    private static final String DEVICE_NAME = "DEVICE_NAME";

    private final Handler chatHandler = new ChatHandler(this);

    public class ChatHandler extends Handler {
        private final WeakReference<BtChatActivity> chatActivity;

        public ChatHandler(BtChatActivity activity) {
            chatActivity = new WeakReference<BtChatActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (chatActivity.get() == null) {
                return;
            }

            switch (msg.what) {
                case Constants.MESSAGE_READ:
                    try {
                        BtMessage btReadMessage = new Gson().fromJson(new String((byte[]) msg.obj, 0, msg.arg1),
                                BtMessage.class);
                        btReadMessage.setContent(userName + ":" + btReadMessage.getContent());
                        btChatService.messageList.add(btReadMessage);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(chatActivity.get(), "数据非法", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    try {
                        BtMessage btWriteMessage = new Gson().fromJson(new String((byte[]) msg.obj), BtMessage.class);
                        btWriteMessage.setContent("Me:" + btWriteMessage.getContent());
                        btChatService.messageList.add(btWriteMessage);
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(chatActivity.get(), "数据非法", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DevicesActivityBtChatLayoutBinding.inflate(layoutInflater);
    }

    @Override
    public void initToolBar() {
        toolbar = ((DevicesActivityBtChatLayoutBinding)viewBinding).toolbar.toolbar;
        initToolBar(toolbar, "Chat", true);
    }

    public static void instance(Context context, String deviceName) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(DEVICE_NAME, deviceName);
        intent.putExtras(bundle);
        intent.setClass(context, BtChatActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        btChatService = BtChatService.getInstance();
        btChatService.setHandler(chatHandler);

        chatListView =  findViewById(R.id.bt_chat_listview);
        editText =  findViewById(R.id.bt_chat_edit);
        sendButton =  findViewById(R.id.bt_chat_sendbt);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        sendButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (btChatService == null) {
                    Log.i("Test", "service is null");
                    return;
                }

                if (btChatService.getState() == BtChatService.STATE_CONNECTED) {
                    String mesg = null;
                    if ((mesg = editText.getText().toString()) != null) {
                        BtMessage btMessage = new BtMessage();
                        btMessage.setContent(mesg);
                        btMessage.setDate(new Date(System.currentTimeMillis()));
                        btMessage.setUserAdress(bluetoothAdapter.getAddress());
                        btMessage.setUserName(bluetoothAdapter.getName());
                        btChatService.write(new Gson().toJson(btMessage).getBytes());

                        editText.setText("");
                    } else {
                        // do nothing
                    }
                } else {
                    Toast.makeText(BtChatActivity.this, "设备未连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userName = getIntent().getStringExtra(DEVICE_NAME);
        if (userName != null && !userName.isEmpty() && toolbar != null) {
            toolbar.setTitle(userName);
        }
        // list = new ArrayList<BtMessage>();
        btChatService.messageList = new ArrayList<BtMessage>();
        adapter = new ChatListAdapter(BtChatActivity.this, btChatService.messageList);
        chatListView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (btChatService != null) {
            if (btChatService.getState() == BtChatService.STATE_NONE) {
                btChatService.start();
            }
        }
    }

    // private boolean sendMessage(BtMessage msg) {
    // if (socket != null && socket.isConnected()) {
    // try {
    // OutputStream os = socket.getOutputStream();
    // msg.setType(MsgType.TRANS);
    // os.write(new Gson().toJson(msg).getBytes());
    // return true;
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // } else {
    // Toast.makeText(BtChatActivity.this, "连接未成功", Toast.LENGTH_SHORT).show();
    // }
    // return false;
    // }
    //
    // private String recvMsg() {
    // if (socket != null && socket.isConnected()) {
    // try {
    // InputStream is = socket.getInputStream();
    // int length = -1;
    // byte[] buffer = new byte[1024];
    // while ((length = is.read(buffer)) > 0) {
    // byte[] data=new byte[length];
    // for(int i=0;i<length;i++){
    // data[i]=buffer[i];
    // }
    // Log.i("Test", "接收到数据");
    // return new String(data);
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // Log.i("Test", e.toString());
    // }
    // } else {
    // Toast.makeText(BtChatActivity.this, "连接未成功", Toast.LENGTH_SHORT).show();
    // }
    // return null;
    //
    // }

}
