package com.wyq.firehelper.developkit.eventbus.eventbus;

public class EventBusMessage {

    private int message = 0;

    public EventBusMessage(int message){
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
