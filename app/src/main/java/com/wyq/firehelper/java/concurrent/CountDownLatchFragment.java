package com.wyq.firehelper.java.concurrent;

import android.view.View;

import com.orhanobut.logger.Logger;
import com.wyq.firehelper.base.BaseCaseFragment;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchFragment extends BaseCaseFragment {


    @Override
    public String[] getArticleFilters() {
        return new String[0];
    }

    @Override
    public String getToolBarTitle() {
        return null;
    }

    @Override
    protected int attachLayoutRes() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {


        try {
            new Driver().start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    class Driver { // ...
        void start() throws InterruptedException {
            int N = 10;
            CountDownLatch startSignal = new CountDownLatch(1);
            CountDownLatch doneSignal = new CountDownLatch(N);

            for (int i = 0; i < N; ++i) // create and start threads
                new Thread(new Worker(startSignal, doneSignal)).start();

            doSomethingElse();            // don't let run yet
            startSignal.countDown();      // let all threads proceed
            doSomethingElse();
            doneSignal.await();           // wait for all to finish

        }

        class Worker implements Runnable {
            private final CountDownLatch startSignal;
            private final CountDownLatch doneSignal;
            Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
                this.startSignal = startSignal;
                this.doneSignal = doneSignal;
            }
            public void run() {
                try {
                    startSignal.await();
                    doWork();
                    doneSignal.countDown();
                } catch (InterruptedException ex) {} // return;
            }

            void doWork() {
                Logger.i("i am working");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }}

    private void doSomethingElse() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
