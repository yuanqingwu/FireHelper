package com.wyq.firehelper.developkit.eventbus.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * @author yuanqingwu
 * @date 2019/05/16
 */
public interface EventObservable<T> {

    /**
     * 发送一个消息，支持前台线程、后台线程发送
     *
     * @param value
     */
    void post(T value);

    /**
     * 延迟发送一个消息，支持前台线程、后台线程发送
     *
     * @param value
     * @param delay 延迟毫秒数
     */
    void postDelay(T value, long delay);

    /**
     * 注册一个Observer，生命周期感知，自动取消订阅
     *
     * @param owner
     * @param observer
     */
    void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer);

    /**
     * 注册一个Observer，生命周期感知，自动取消订阅
     * 如果之前有消息发送，可以在注册时收到消息（消息同步）
     *
     * @param owner
     * @param observer
     */
    void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer);

    /**
     * 注册一个Observer
     *
     * @param observer
     */
    void observeForever(@NonNull Observer<T> observer);

    /**
     * 注册一个Observer
     * 如果之前有消息发送，可以在注册时收到消息（消息同步）
     *
     * @param observer
     */
    void observeStickyForever(@NonNull Observer<T> observer);

    /**
     * 通过observeForever或observeStickyForever注册的，需要调用该方法取消订阅
     *
     * @param observer
     */
    void removeObserver(@NonNull Observer<T> observer);
}
