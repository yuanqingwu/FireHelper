package com.wyq.firehelper.java.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author yuanqingwu
 * @date 2019/06/20
 */
public class LockTest {

    public static void main(String[] args){

        ReentrantLock lock = new ReentrantLock();

        Condition condition = lock.newCondition();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("i am locked"+System.currentTimeMillis());
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
                System.out.println("i am notified"+System.currentTimeMillis());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    Thread.sleep(5000);

                    condition.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
                System.out.println("signal ~~~"+System.currentTimeMillis());


            }
        }).start();


    }
}
