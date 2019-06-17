package com.wyq.firehelper.developkit.eventbus.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 最简单的RxBus实现
 * @author yuanqingwu
 * @date 2019/05/16
 */
public class RxBusSimple {

    private final Subject<Object> mBus;

    private RxBusSimple(){
        //Use {@link #toSerialized()} to make bus thread-safe.
        mBus = PublishSubject.create().toSerialized();
    }

    private static class Holder{
        private static final RxBusSimple RX_BUS_SIMPLE = new RxBusSimple();
    }

    public static RxBusSimple get(){
        return Holder.RX_BUS_SIMPLE;
    }

    public void post(Object o){
        if(mBus != null){
            mBus.onNext(o);
        }
    }

    public Observable<Object> toObservable(){
        return mBus;
    }

    public <T> Observable<T> toObservable(Class<T> type){
        return mBus.ofType(type);
    }

    public boolean hasObservable(){
        return mBus.hasObservers();
    }
}
