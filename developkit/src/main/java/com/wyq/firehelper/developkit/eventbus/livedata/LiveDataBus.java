package com.wyq.firehelper.developkit.eventbus.livedata;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuanqingwu
 * @date 2019/05/16
 */
public class LiveDataBus {

    private final Map<String, LiveDataEvent<Object>> mBus;

    private LiveDataBus(){
        mBus = new HashMap<>();
    }

    private static class Holder{
        private static final LiveDataBus bus = new LiveDataBus();
    }

    public static LiveDataBus get(){
        return Holder.bus;
    }

    public <T> EventObservable<T> with(String key,Class<T> type){

        if(!mBus.containsKey(key)){
            mBus.put(key,new LiveDataEvent(key));
        }
        return (EventObservable<T>) mBus.get(key);
    }

    public EventObservable<Object> with(String key){
        return with(key,Object.class);
    }

    protected void remove(String key){
        if(mBus != null && mBus.containsKey(key)){
            mBus.remove(key);
        }
    }
}
