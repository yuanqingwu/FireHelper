package com.wyq.firehelper;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil {


    public static <T> T getValue(LiveData<T> liveData) throws InterruptedException {
        Object[] data = new Object[1];
        CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                data[0] = t;
                latch.countDown();
                //Once we got a notification via onChanged, we stop observing.
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        //We're waiting for LiveData to emit, for 2 seconds.
        latch.await(2, TimeUnit.SECONDS);
        return (T)data[0];
    }
}
