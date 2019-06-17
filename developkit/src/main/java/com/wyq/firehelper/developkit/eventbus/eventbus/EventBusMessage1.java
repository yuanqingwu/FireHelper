package com.wyq.firehelper.developkit.eventbus.eventbus;

public class EventBusMessage1 {
    private int message = 0;

    public EventBusMessage1(int message){
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
