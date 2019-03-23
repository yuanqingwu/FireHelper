package com.wyq.firehelper.device.bluetooth.ble;

import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * todo :完善接口封装
 *
 * @author Uni.W
 * @date 2019/3/23 13:44
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BleManager {
    private final static String TAG = BleManager.class.getSimpleName();

    private Context context;

    private BluetoothLeService mBluetoothLeService;
    private String mDeviceAddress;
    private boolean mConnected = false;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
            }
        }
    };

    /**
     * 是否支持BLE
     *
     * @return
     */
    public boolean isSupportBLE() {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public void createBLEService() {
        Intent gattServiceIntent = new Intent(context, BluetoothLeService.class);
        context.bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public boolean isConnected(){
        return mConnected;
    }

    public void closeBLEService() {
        context.unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    public void connect(String mDeviceAddress){
        if(mBluetoothLeService != null){
            mBluetoothLeService.connect(mDeviceAddress);
        }
        context.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }


    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    public void disconnect(){
        if(mGattUpdateReceiver != null){
            context.unregisterReceiver(mGattUpdateReceiver);
        }
        if(mBluetoothLeService != null){
            mBluetoothLeService.disconnect();
        }
    }


    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothLeService == null) {
            return null;
        }
        return mBluetoothLeService.getSupportedGattServices();
    }
}
