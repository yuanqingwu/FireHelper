package com.wyq.firehelper.java.algorithm.ProducerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Uni.W
 * @date 2019/3/20 16:53
 */
public class LockConditionConsumerProducer {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition producerCondition = lock.newCondition();
        Condition consumerCondition = lock.newCondition();
        Resource2 resource = new Resource2(lock,producerCondition,consumerCondition);

        //生产者线程
        ProducerThread2 producer1 = new ProducerThread2(resource);

        //消费者线程
        ConsumerThread2 consumer1 = new ConsumerThread2(resource);
        ConsumerThread2 consumer2 = new ConsumerThread2(resource);
        ConsumerThread2 consumer3 = new ConsumerThread2(resource);

        producer1.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}
/**
 * 消费者线程
 */
class ConsumerThread2 extends Thread{
    private Resource2 resource;
    public ConsumerThread2(Resource2 resource){
        this.resource = resource;
        //setName("消费者");
    }
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.remove();
        }
    }
}
/**
 * 生产者线程
 *
 */
class ProducerThread2 extends Thread{
    private Resource2 resource;
    public ProducerThread2(Resource2 resource){
        this.resource = resource;
        setName("生产者");
    }
    @Override
    public void run(){
        while(true){
            try {
                Thread.sleep((long) (1000 * Math.random()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.add();
        }
    }
}
/**
 * 公共资源类
 *
 */
class Resource2{
    private int num = 0;//当前资源数量
    private int size = 10;//资源池中允许存放的资源数目
    private Lock lock;
    private Condition producerCondition;
    private Condition consumerCondition;
    public Resource2(Lock lock, Condition producerCondition, Condition consumerCondition) {
        this.lock = lock;
        this.producerCondition = producerCondition;
        this.consumerCondition = consumerCondition;

    }
    /**
     * 向资源池中添加资源
     */
    public void add(){
        lock.lock();
        try{
            if(num < size){
                num++;
                System.out.println(Thread.currentThread().getName() +
                        "生产一件资源,当前资源池有" + num + "个");
                //唤醒等待的消费者
                consumerCondition.signalAll();
            }else{
                //让生产者线程等待
                try {
                    producerCondition.await();
                    System.out.println(Thread.currentThread().getName() + "线程进入等待");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }finally{
            lock.unlock();
        }
    }
    /**
     * 从资源池中取走资源
     */
    public void remove(){
        lock.lock();
        try{
            if(num > 0){
                num--;
                System.out.println("消费者" + Thread.currentThread().getName()
                        + "消耗一件资源," + "当前资源池有" + num + "个");
                producerCondition.signalAll();//唤醒等待的生产者
            }else{
                try {
                    consumerCondition.await();
                    System.out.println(Thread.currentThread().getName() + "线程进入等待");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }//让消费者等待
            }
        }finally{
            lock.unlock();
        }
    }
}
