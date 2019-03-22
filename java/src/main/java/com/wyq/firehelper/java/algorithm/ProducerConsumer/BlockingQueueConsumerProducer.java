package com.wyq.firehelper.java.algorithm.ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Uni.W
 * @date 2019/3/20 16:48
 */
public class BlockingQueueConsumerProducer {
    public static void main(String[] args) {
        Resource3 resource = new Resource3();
        //生产者线程
        ProducerThread3 p = new ProducerThread3(resource);
        //多个消费者
        ConsumerThread3 c1 = new ConsumerThread3(resource);
        ConsumerThread3 c2 = new ConsumerThread3(resource);
        ConsumerThread3 c3 = new ConsumerThread3(resource);

        p.start();
        c1.start();
        c2.start();
        c3.start();
    }
}
/**
 * 消费者线程
 *
 */
class ConsumerThread3 extends Thread {
    private Resource3 resource3;

    public ConsumerThread3(Resource3 resource) {
        this.resource3 = resource;
        //setName("消费者");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource3.remove();
        }
    }
}
/**
 * 生产者线程
 *
 */
class ProducerThread3 extends Thread{
    private Resource3 resource3;
    public ProducerThread3(Resource3 resource) {
        this.resource3 = resource;
        //setName("生产者");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource3.add();
        }
    }
}
class Resource3{
    private BlockingQueue resourceQueue = new LinkedBlockingQueue(10);
    /**
     * 向资源池中添加资源
     */
    public void add(){
        try {
            resourceQueue.put(1);
            System.out.println("生产者" + Thread.currentThread().getName()
                    + "生产一件资源," + "当前资源池有" + resourceQueue.size() +
                    "个资源");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 向资源池中移除资源
     */
    public void remove(){
        try {
            resourceQueue.take();
            System.out.println("消费者" + Thread.currentThread().getName() +
                    "消耗一件资源," + "当前资源池有" + resourceQueue.size()
                    + "个资源");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
