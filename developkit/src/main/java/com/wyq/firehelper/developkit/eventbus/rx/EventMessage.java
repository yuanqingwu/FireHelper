package com.wyq.firehelper.developkit.eventbus.rx;

/**
 * @author yuanqingwu
 * @date 2019/05/09
 */
public class EventMessage {

    private String tag;
    private Object action;

    public EventMessage(String tag, Object action){
        this.tag = tag;
        this.action = action;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getAction() {
        return action;
    }

    public void setAction(Object action) {
        this.action = action;
    }


    public boolean isSameType(final Class eventType,final String tag){
        return BusUtils.equals(BusUtils.getClassFromObject(action),eventType)
                && BusUtils.equals(this.tag,tag);
    }
}
