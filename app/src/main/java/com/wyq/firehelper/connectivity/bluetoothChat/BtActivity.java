package com.wyq.firehelper.connectivity.bluetoothChat;

import java.util.LinkedList;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wyq.firehelper.R;

public class BtActivity extends BtBaseActivity implements OnClickListener {

    private Button scanBt, closeBt, backBt, discoverBt;
    private BtReceiver btReceiver;
    private ListView listView;
    private LinkedList<String> btInfoLinkedList;
    private ArrayAdapter<String> arrayAdapter;
    public BluetoothSocket socket;
    public BluetoothDevice device;
    public BluetoothAdapter bluetoothAdapter;

    private String deviceName; // 连接设备的名字

    private static final int REQUEST_ENABLE_BT = 3;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BtChatService.STATE_CONNECTED:

                            break;
                        case BtChatService.STATE_CONNECTING:
                            // 连接ing
                            break;
                        case BtChatService.STATE_LISTEN:
                        case BtChatService.STATE_NONE:
                            // 未连接
                            break;

                        default:
                            break;
                    }
                    break;
                case Constants.MESSAGE_READ:
                    BtMessage btReadMessage = new Gson().fromJson(new String((byte[]) msg.obj, 0, msg.arg1),
                            BtMessage.class);
                    btReadMessage.setContent(deviceName + ":" + btReadMessage.getContent());
                    messageList.add(btReadMessage);
                    BtChatActivity.adapter.notifyDataSetChanged();
                    break;
                case Constants.MESSAGE_WRITE:
                    BtMessage btWriteMessage = new Gson().fromJson(new String((byte[]) msg.obj), BtMessage.class);
                    btWriteMessage.setContent("Me:" + btWriteMessage.getContent());
                    messageList.add(btWriteMessage);
                    BtChatActivity.adapter.notifyDataSetChanged();
                    break;

                case Constants.MESSAGE_DEVICE_NAME:
                    deviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    Toast.makeText(BtActivity.this, "Connected to " + deviceName, Toast.LENGTH_SHORT).show();
                    // 连接成功
                    Intent intent = new Intent();
                    intent.putExtra("btname", deviceName);
                    intent.setClass(BtActivity.this, BtChatActivity.class);
                    startActivity(intent);
                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(BtActivity.this, msg.getData().getString(Constants.TOAST), Toast.LENGTH_SHORT)
                            .show();
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bt);
        listView = (ListView) findViewById(R.id.bluetooth_finded_list);
        btInfoLinkedList = new LinkedList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        arrayAdapter = new ArrayAdapter<String>(BtActivity.this, android.R.layout.simple_list_item_1, btInfoLinkedList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = btInfoLinkedList.get(position).substring(0, 17);
                btChatService.connect(bluetoothAdapter.getRemoteDevice(address), true);
            }
        });

        backBt = (Button) findViewById(R.id.bluetooth_back_bt);
        backBt.setOnClickListener(this);
        discoverBt = (Button) findViewById(R.id.bluetooth_discoverable_bt);
        discoverBt.setOnClickListener(this);
        scanBt = (Button) findViewById(R.id.bluetooth_start_scan_bt);
        scanBt.setOnClickListener(this);
        closeBt = (Button) findViewById(R.id.bluetooth_close_bt);
        closeBt.setOnClickListener(this);

        btReceiver = new BtReceiver();
        // 注册广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(btReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(btReceiver, filter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bluetooth_start_scan_bt:
                setProgressBarIndeterminateVisibility(true);
                checkEnable();
                // 如果正在搜寻，先关闭
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();

                break;
            case R.id.bluetooth_close_bt:
                BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
                if (adapter.isDiscovering()) {
                    adapter.cancelDiscovery();
                }
                if (adapter.isEnabled()) {
                    adapter.disable();
                }
                // btInfoList.clear();
                btInfoLinkedList.clear();
                arrayAdapter.notifyDataSetChanged();
                break;
            case R.id.bluetooth_back_bt:
                finish();
                break;
            case R.id.bluetooth_discoverable_bt:
                makeDiscoverable();
                break;
            default:
                break;
        }
    }

    private void checkEnable() {
        if (bluetoothAdapter != null) {
            if (!bluetoothAdapter.isEnabled()) {
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT);
            }
        } else {
            Log.i("Test", "没有蓝牙设备");
        }
    }

    private void addBondedDevice() {
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if (!bondedDevices.isEmpty()) {
            for (BluetoothDevice device : bondedDevices) {
                if (!btInfoLinkedList.contains(device.getAddress() + "           " + device.getName() + "           "
                        + "已配对")) {
                    btInfoLinkedList.addFirst(device.getAddress() + "           " + device.getName() + "           "
                            + "已配对");
                }
            }
            arrayAdapter.notifyDataSetChanged();
        }
    }

    private void makeDiscoverable() {
        if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // makeDiscoverable();
        checkEnable();
        addBondedDevice();
        if (btChatService == null) {
            btChatService = new BtChatService(BtActivity.this, handler);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bluetoothAdapter.isEnabled()) {
            if (btChatService != null) {
                if (btChatService.getState() == BtChatService.STATE_NONE) {
                    btChatService.start();
                }
            }
        }
    }

    private class BtReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    if (!btInfoLinkedList.contains(device.getAddress() + "           " + device.getName())) {
                        // btDeviceList.add(device);
                        btInfoLinkedList.add(device.getAddress() + "           " + device.getName());
                        arrayAdapter.notifyDataSetChanged();
                        Log.i("Test", "find device:" + device.getName() + device.getAddress());
                    }
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    // 搜索完成
                    setProgressBarIndeterminateVisibility(false);
                    Log.i("Test", "搜索完成");
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(BtActivity.this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
                    addBondedDevice();
                } else {
                    Toast.makeText(BtActivity.this, "Bluetooth was not enabled. Leaving Bluetooth Chat.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(btReceiver);
        btChatService.stop();
        super.onDestroy();
    }

}
