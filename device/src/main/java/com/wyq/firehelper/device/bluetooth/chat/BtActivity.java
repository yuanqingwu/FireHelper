package com.wyq.firehelper.device.bluetooth.chat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.wyq.firehelper.base.BaseActivity;
import com.wyq.firehelper.device.R;
import com.wyq.firehelper.device.databinding.DevicesActivityBtBinding;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

public class BtActivity extends BaseActivity implements OnClickListener {

    public Toolbar toolbar;
    public Button scanBt;
    public Button closeBt;
    public ListView listView;

    private BtReceiver btReceiver;
    private LinkedList<String> btInfoLinkedList;
    private ArrayAdapter<String> arrayAdapter;
    public BluetoothSocket socket;
    public BluetoothDevice device;
    public BluetoothAdapter bluetoothAdapter;

    private BtChatService btChatService;

    public String deviceName; // 连接设备的名字

    private static final int REQUEST_ENABLE_BT = 3;

    public BtHandler handler = new BtHandler(this);

    public static class BtHandler extends Handler {
        public WeakReference<BtActivity> btActivityWeakReference;

        public BtHandler(BtActivity btActivity) {
            btActivityWeakReference = new WeakReference<BtActivity>(btActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BtActivity btActivity = btActivityWeakReference.get();
            if (btActivity != null) {
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
                    case Constants.MESSAGE_DEVICE_NAME:
                        btActivity.deviceName = msg.getData().getString(Constants.DEVICE_NAME);
                        Toast.makeText(btActivity, "Connected to " + btActivity.deviceName, Toast.LENGTH_SHORT).show();
                        // 连接成功
                        BtChatActivity.instance(btActivity,btActivity.deviceName);
                        break;
                    case Constants.MESSAGE_TOAST:
                        Toast.makeText(btActivity, msg.obj.toString(), Toast.LENGTH_SHORT)
                                .show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected ViewBinding inflateViewBinding(@NonNull LayoutInflater layoutInflater) {
        return DevicesActivityBtBinding.inflate(layoutInflater);
    }

    @Override
    public void initToolBar() {
        initToolBar(toolbar, "BT", true);
    }

    @Override
    public void initView() {
        toolbar = ((DevicesActivityBtBinding)viewBinding).toolbar.toolbar;
        scanBt = ((DevicesActivityBtBinding)viewBinding).deviceBtStartScanBt;
        closeBt = ((DevicesActivityBtBinding)viewBinding).deviceBtCloseBt;
        listView = ((DevicesActivityBtBinding)viewBinding).deviceBtFindedListLv;

        btInfoLinkedList = new LinkedList<String>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        makeDiscoverable();
        btChatService = BtChatService.getInstance();
        btChatService.setHandler(handler);

        arrayAdapter = new ArrayAdapter<String>(BtActivity.this, android.R.layout.simple_list_item_1, btInfoLinkedList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String address = btInfoLinkedList.get(position).substring(0, 17);
                btChatService.connect(bluetoothAdapter.getRemoteDevice(address), true);
            }
        });

//        discoverBt = (Button) findViewById(R.id.bluetooth_discoverable_bt);
//        discoverBt.setOnClickListener(this);
        scanBt.setOnClickListener(this);
        closeBt.setOnClickListener(this);

        btReceiver = new BtReceiver();
        // 注册广播
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(btReceiver, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(btReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // makeDiscoverable();
        checkEnable();
        addBondedDevice();
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

    private void startDiscovery(){
        checkEnable();
        // 如果正在搜寻，先关闭
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        bluetoothAdapter.startDiscovery();
    }

    private void disableBt(){

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        }
        // btInfoList.clear();
        btInfoLinkedList.clear();
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.device_bt_start_scan_bt) {
            startDiscovery();
        } else if (i == R.id.device_bt_close_bt) {
            disableBt();
        } else {
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
                    Log.i("Test", "搜索完成");
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(BtActivity.this, "Bluetooth enabled", Toast.LENGTH_SHORT).show();
                addBondedDevice();
            } else {
                Toast.makeText(BtActivity.this, "Bluetooth was not enabled. Leaving Bluetooth Chat.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(btReceiver);
        btChatService.stop();
        super.onDestroy();
    }

}
