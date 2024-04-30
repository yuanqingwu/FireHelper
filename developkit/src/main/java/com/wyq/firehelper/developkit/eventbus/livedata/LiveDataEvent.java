package com.wyq.firehelper.developkit.eventbus.livedata;

import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.ExLiveData;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * @author yuanqingwu
 * @date 2019/05/16
 */
public class LiveDataEvent<T> implements EventObservable<T> {

    private final String key;

    private final boolean lifecycleObserverAlwaysActive = true;
    private final LifecycleLiveData<T> liveData;
    private final Map<Observer, ObserverWrapper<T>> observerMap = new HashMap<>();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public LiveDataEvent(String key) {
        this.key = key;
        liveData = new LifecycleLiveData<>();
    }

    @Override
    public void post(T value) {
        if (isMainThread()) {
            liveData.setValue(value);
        } else {
            liveData.postValue(value);
        }
    }

    @Override
    public void postDelay(T value, long delay) {
        if (isMainThread()) {
            liveData.setValue(value);
        } else {
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    liveData.setValue(value);
                }
            }, delay);
        }
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        boolean ignoreUtilObserver = liveData.getVersion() > ExLiveData.START_VERSION;

        if (isMainThread()) {
            observeInternal(owner, observer, ignoreUtilObserver);
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    observeInternal(owner, observer, ignoreUtilObserver);
                }
            });
        }
    }

    @Override
    public void observeSticky(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
        if (isMainThread()) {
            observeInternal(owner, observer, false);
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    observeInternal(owner, observer, false);
                }
            });
        }
    }

    @Override
    public void observeForever(@NonNull Observer<T> observer) {
        boolean ignoreUtilObserver = liveData.getVersion() > ExLiveData.START_VERSION;
        if (isMainThread()) {
            observeForeverInternal(observer, ignoreUtilObserver);
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    observeForeverInternal(observer, ignoreUtilObserver);
                }
            });
        }
    }

    @Override
    public void observeStickyForever(@NonNull Observer<T> observer) {
        if (isMainThread()) {
            observeForeverInternal(observer, false);
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    observeForeverInternal(observer, false);
                }
            });
        }
    }

    @Override
    public void removeObserver(@NonNull Observer<T> observer) {
        Observer<T> realObserver;
        if (observerMap.containsKey(observer)) {
            realObserver = observerMap.remove(observer);
        } else {
            realObserver = observer;
        }
        liveData.removeObserver(realObserver);
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    @MainThread
    private void observeInternal(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer, boolean ignoreUtilObserver) {
        ObserverWrapper<T> observerWrapper = new ObserverWrapper<>(observer);
        observerWrapper.ignoreUtilObserver = ignoreUtilObserver;
        liveData.observe(owner, observerWrapper);
    }

    @MainThread
    private void observeForeverInternal(@NonNull Observer<T> observer, boolean ignoreUtilObserver) {
        ObserverWrapper<T> observerWrapper = new ObserverWrapper<>(observer);
        observerWrapper.ignoreUtilObserver = ignoreUtilObserver;
        observerMap.put(observer, observerWrapper);
        liveData.observeForever(observerWrapper);
    }

    private class LifecycleLiveData<T> extends ExLiveData<T> {

        @Override
        protected Lifecycle.State observerActiveLevel() {
            //是否全程订阅
            return lifecycleObserverAlwaysActive ? Lifecycle.State.CREATED : Lifecycle.State.STARTED;
        }


        @Override
        public void removeObserver(@NonNull Observer<? super T> observer) {
            super.removeObserver(observer);
            //如果没有监听者则删除
            if (!liveData.hasObservers()) {
                LiveDataBus.get().remove(key);
            }
        }
    }

    private static class ObserverWrapper<T> implements Observer<T> {

        @NonNull
        private final Observer<T> mObserver;

        private boolean ignoreUtilObserver = true;

        ObserverWrapper(Observer<T> mObserver) {
            this.mObserver = mObserver;
        }

        @Override
        public void onChanged(T t) {
//            Logger.i("onChanged ignoreUtilObserver:"+ignoreUtilObserver);
            if (ignoreUtilObserver) {
                ignoreUtilObserver = false;
                return;
            }
            mObserver.onChanged(t);
        }
    }
}
