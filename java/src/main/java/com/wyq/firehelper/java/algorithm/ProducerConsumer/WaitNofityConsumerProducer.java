package com.wyq.firehelper.java.algorithm.ProducerConsumer;

/**
 * @author Uni.W
 * @date 2019/3/20 16:41
 */
public class WaitNofityConsumerProducer {
    public static void main(String[] args) {
        Resource resource = new Resource();
        //生产者线程
        ProducerThread p1 = new ProducerThread(resource);
        ProducerThread p2 = new ProducerThread(resource);
        ProducerThread p3 = new ProducerThread(resource);
        //消费者线程
        ConsumerThread c1 = new ConsumerThread(resource);
        //ConsumerThread c2 = new ConsumerThread(resource);
        //ConsumerThread c3 = new ConsumerThread(resource);

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        //c2.start();
        //c3.start();
    }



}
/**
 * 公共资源类
 * @author
 *
 */
class Resource{//重要
    //当前资源数量
    private int num = 0;
    //资源池中允许存放的资源数目
    private final int size = 10;

    /**
     * 从资源池中取走资源
     */
    public synchronized void remove(){
        if(num > 0){
            num--;
            System.out.println("消费者" + Thread.currentThread().getName() +
                    "消耗一件资源，" + "当前线程池有" + num + "个");
            notifyAll();//通知生产者生产资源
        }else{
            try {
                //如果没有资源，则消费者进入等待状态
                wait();
                System.out.println("消费者" + Thread.currentThread().getName() + "线程进入等待状态");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 向资源池中添加资源
     */
    public synchronized void add(){
        if(num < size){
            num++;
            System.out.println(Thread.currentThread().getName() + "生产一件资源，当前资源池有"
                    + num + "个");
            //通知等待的消费者
            notifyAll();
        }else{
            //如果当前资源池中有10件资源
            try{
                wait();//生产者进入等待状态，并释放锁
                System.out.println(Thread.currentThread().getName()+"线程进入等待");
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
/**
 * 消费者线程
 */
class ConsumerThread extends Thread{
    private final Resource resource;
    public ConsumerThread(Resource resource){
        this.resource = resource;
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.remove();
        }
    }
}
/**
 * 生产者线程
 */
class ProducerThread extends Thread{
    private final Resource resource;
    public ProducerThread(Resource resource){
        this.resource = resource;
    }
    @Override
    public void run() {
        //不断地生产资源
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resource.add();
        }
    }
}
