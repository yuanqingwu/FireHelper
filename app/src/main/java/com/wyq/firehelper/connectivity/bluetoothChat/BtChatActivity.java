package com.wyq.firehelper.connectivity.bluetoothChat;

import java.util.ArrayList;
import java.util.Date;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wyq.firehelper.R;

public class BtChatActivity extends BtBaseActivity {

    private TextView titleTextView;
    private ListView chatListView;
    private EditText editText;
    private Button sendButton;

    private String userName;
    public static ChatListAdapter adapter;
    // private List<BtMessage> list;
    private BluetoothAdapter bluetoothAdapter;

    // private BtChatService btChatService = BtActivity.btChatService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bt_chat_layout);
        titleTextView = (TextView) findViewById(R.id.bt_chat_title);
        chatListView = (ListView) findViewById(R.id.bt_chat_listview);
        editText = (EditText) findViewById(R.id.bt_chat_edit);
        sendButton = (Button) findViewById(R.id.bt_chat_sendbt);

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

        userName = getIntent().getStringExtra("btname");

        titleTextView.setText(userName);
        // list = new ArrayList<BtMessage>();
        messageList = new ArrayList<BtMessage>();
        adapter = new ChatListAdapter(BtChatActivity.this, messageList);
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
            if (btChatService.getState() == btChatService.STATE_NONE) {
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
