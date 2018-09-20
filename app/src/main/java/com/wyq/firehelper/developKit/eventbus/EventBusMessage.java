package com.wyq.firehelper.developKit.eventbus;

public class EventBusMessage {

    /**
     * 网页回退
     */
    public static final int WEBVIEW_GO_BACK = 1;
    public static final int WEBVIEW_GO_FORWARD = 2;

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
