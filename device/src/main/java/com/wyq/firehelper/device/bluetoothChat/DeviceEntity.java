package com.wyq.firehelper.device.bluetoothChat;

/**
 * @author Uni.W
 * @date 2019/3/22 16:52
 */
public class DeviceEntity {
    private String mac;
    private String model;
    private String name;
    private String state;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
