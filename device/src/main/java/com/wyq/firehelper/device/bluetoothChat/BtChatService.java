package com.wyq.firehelper.device.bluetoothChat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public class BtChatService {

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0; // we're doing nothing
    public static final int STATE_LISTEN = 1; // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3; // now connected to a remote device

    private Handler handler;
    private int state;
    private AcceptThread secureAcceptThread;
    private AcceptThread insecureAcceptThread;
    private ConnectThread connectThread;
    private ChatThread chatThread;

    private BluetoothAdapter adapter;

    public List<BtMessage> messageList;

    private static volatile BtChatService chatService;

    private BtChatService() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        state = STATE_NONE;
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    public static BtChatService getInstance(){
        if(chatService == null){
            synchronized (BtChatService.class){
                if(chatService == null) {
                    chatService = new BtChatService();
                }
            }
        }
        return chatService;
    }

    private synchronized void setState(int state) {
        this.state = state;
        handler.obtainMessage(Constants.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }

    public synchronized int getState() {
        return state;
    }

    public synchronized void start() {
        // Cancel any thread attempting to make a connection
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        // Cancel any thread currently running a connection
        if (chatThread != null) {
            chatThread.cancel();
            chatThread = null;
        }

        setState(STATE_LISTEN);

        // Start the thread to listen on a BluetoothServerSocket
        if (secureAcceptThread == null) {
            secureAcceptThread = new AcceptThread(true);
            secureAcceptThread.start();
        }
        if (insecureAcceptThread == null) {
            insecureAcceptThread = new AcceptThread(false);
            insecureAcceptThread.start();
        }
    }

    public synchronized void connect(BluetoothDevice device, boolean secure) {
        if (state == STATE_CONNECTING) {
            if (connectThread != null) {
                connectThread.cancel();
                connectThread = null;
            }
        }
        if (chatThread != null) {
            chatThread.cancel();
            chatThread = null;
        }
        connectThread = new ConnectThread(device, secure);
        connectThread.start();
        setState(STATE_CONNECTING);
    }

    public synchronized void chat(BluetoothSocket socket, BluetoothDevice device) {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        if (chatThread != null) {
            chatThread.cancel();
            chatThread = null;
        }

        // Cancel the accept thread because we only want to connect to one device
        if (secureAcceptThread != null) {
            secureAcceptThread.cancel();
            secureAcceptThread = null;
        }
        if (insecureAcceptThread != null) {
            insecureAcceptThread.cancel();
            insecureAcceptThread = null;
        }

        chatThread = new ChatThread(socket);
        chatThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = handler.obtainMessage(Constants.MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.DEVICE_NAME, device.getName());
        msg.setData(bundle);
        handler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }

    public synchronized void stop() {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (chatThread != null) {
            chatThread.cancel();
            chatThread = null;
        }

        if (secureAcceptThread != null) {
            secureAcceptThread.cancel();
            secureAcceptThread = null;
        }

        if (insecureAcceptThread != null) {
            insecureAcceptThread.cancel();
            insecureAcceptThread = null;
        }
        setState(STATE_NONE);
    }

    public void write(byte[] out) {
        ChatThread mchatThread;
        synchronized (this) {
            if (state != STATE_CONNECTED) {
                return;
            }
            mchatThread = chatThread;
        }
        mchatThread.write(out);
    }

    private class AcceptThread extends Thread {
        private BluetoothServerSocket serverSocket;

        public AcceptThread(boolean secure) {
            BluetoothServerSocket tmp = null;
            try {
                if (secure) {
                    tmp = adapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);
                } else {
                    tmp = adapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverSocket = tmp;
        }

        @Override
        public void run() {
            BluetoothSocket bluetoothSocket;
            while (state != STATE_CONNECTED) {
                try {
                    bluetoothSocket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

                if (bluetoothSocket != null && bluetoothSocket.isConnected()) {
                    synchronized (BtChatService.this) {
                        switch (state) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                chat(bluetoothSocket, bluetoothSocket.getRemoteDevice());
                                break;

                            case STATE_CONNECTED:
                            case STATE_NONE:
                                try {
                                    bluetoothSocket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }

        public void cancel() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ConnectThread extends Thread {
        private BluetoothDevice device;
        private BluetoothSocket socket;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            this.device = device;
            BluetoothSocket tmp = null;
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
                } else {
                    tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket = tmp;
        }

        @Override
        public void run() {
            super.run();
            adapter.cancelDiscovery();

            try {
                socket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                Message.obtain(handler, Constants.MESSAGE_TOAST, "unable to connect device").sendToTarget();
               BtChatService.this.start();
                return;
            }
            synchronized (BtChatService.this) {
                connectThread = null;
            }
            chat(socket, device);
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ChatThread extends Thread {
        private BluetoothSocket socket;
        private InputStream is;
        private OutputStream os;

        public ChatThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream tmpIn=null;
            OutputStream tmpOut=null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            is=tmpIn;
            os=tmpOut;
        }

        @Override
        public void run() {
            super.run();
            byte[] buffer = new byte[1024];
            int length;
            while (true) {
                try {
                    length = is.read(buffer);
                    handler.obtainMessage(Constants.MESSAGE_READ, length, -1, buffer).sendToTarget();

                } catch (IOException e) {
                    e.printStackTrace();
                    handler.obtainMessage(Constants.MESSAGE_TOAST, "device lost connection").sendToTarget();
                    BtChatService.this.start();
                    break;
                }
            }

        }

        public void write(byte[] buffer) {
            try {
                os.write(buffer);
                handler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
