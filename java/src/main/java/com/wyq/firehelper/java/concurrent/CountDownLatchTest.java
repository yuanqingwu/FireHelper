package com.wyq.firehelper.java.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * todo CyclicBarrier
 */
public class CountDownLatchTest {

    public static void main(String[] args){

        Driver driver = new Driver();
        try {
            driver.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

     static class Driver { // ...
        void start() throws InterruptedException {
            int N = 10;
            CountDownLatch startSignal = new CountDownLatch(1);
            CountDownLatch doneSignal = new CountDownLatch(N);

            for (int i = 0; i < N; ++i) { // create and start threads
                Thread thread = new Thread(new Worker(startSignal, doneSignal,i));
                thread.setName(""+i);
                thread.start();
            }

            doSomethingElse();            // don't let run yet
            startSignal.countDown();      // let all threads proceed
            doSomethingElse();
            doneSignal.await();           // wait for all to finish

        }
         private void doSomethingElse() {
             System.out.println("doSomethingElse for 5 sec");
             try {
                 Thread.sleep(5000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
    }
        static class Worker implements Runnable {
            private final CountDownLatch startSignal;
            private final CountDownLatch doneSignal;
            private int id = 0;
            Worker(CountDownLatch startSignal, CountDownLatch doneSignal,int id) {
                this.startSignal = startSignal;
                this.doneSignal = doneSignal;
                this.id = id;
            }
            public void run() {
                try {
                    startSignal.await();
                    doWork();
                    doneSignal.countDown();
                } catch (InterruptedException ex) {} // return;
            }

            void doWork() {
                System.out.println("Thread "+id+" working for 2 sec");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}
