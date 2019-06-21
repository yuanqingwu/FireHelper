package com.wyq.firehelper.java.concurrent;

import android.os.Build;

import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

import androidx.annotation.RequiresApi;

/**
 * @author yuanqingwu
 * @date 2019/06/17
 */
public class QueueTest {


    public static void main(String[] args) {

    }


    /**
     * LinkedBlockingQueue是一个用链表实现的有界阻塞队列。此队列的默认和最大长度为Integer.MAX_VALUE。此队列按照先进先出的原则对元素进行排序。
     */
    public static void testBlockingQueue() {
        /**
         * LinkedBlockingQueue是一个用链表实现的有界阻塞队列。此队列的默认和最大长度为Integer.MAX_VALUE。此队列按照先进先出的原则对元素进行排序。
         */
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(1000);//不保证公平
        /**
         * ArrayBlockingQueue是一个用数组实现的有界阻塞队列。此队列按照先进先出（FIFO）的原则对元素进行排序。默认情况下不保证访问者公平的访问队列，所谓公平访问队列是指阻塞的所有生产者线程或消费者线程，当队列可用时，可以按照阻塞的先后顺序访问队列，即先阻塞的生产者线程，可以先往队列里插入元素，先阻塞的消费者线程，可以先从队列里获取元素。通常情况下为了保证公平性会降低吞吐量。我们可以使用以下代码创建一个公平的阻塞队列：
         */
        BlockingQueue<Integer> arrayBlockingQueue1 = new ArrayBlockingQueue<>(1000,true);//公平队列
        /**
         * PriorityBlockingQueue是一个支持优先级的无界队列。默认情况下元素采取自然顺序排列，也可以通过比较器comparator来指定元素的排序规则。元素按照升序排列。
         */
        BlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        /**
         * SynchronousQueue是一个不存储元素的阻塞队列。每一个put操作必须等待一个take操作，否则不能继续添加元素。SynchronousQueue可以看成是一个传球手，负责把生产者线程处理的数据直接传递给消费者线程。队列本身并不存储任何元素，非常适合于传递性场景,比如在一个线程中使用的数据，传递给另外一个线程使用，SynchronousQueue的吞吐量高于LinkedBlockingQueue 和 ArrayBlockingQueue。
         */
        BlockingQueue<Integer> synchronousQueue = new SynchronousQueue<>();

        /**
         * DelayQueue是一个支持延时获取元素的无界阻塞队列。队列使用PriorityQueue来实现。队列中的元素必须实现Delayed接口，在创建元素时可以指定多久才能从队列中获取当前元素。只有在延迟期满时才能从队列中提取元素。我们可以将DelayQueue运用在以下应用场景：
         *
         * 缓存系统的设计：可以用DelayQueue保存缓存元素的有效期，使用一个线程循环查询DelayQueue，一旦能从DelayQueue中获取元素时，表示缓存有效期到了。
         * 定时任务调度。使用DelayQueue保存当天将会执行的任务和执行时间，一旦从DelayQueue中获取到任务就开始执行，从比如TimerQueue就是使用DelayQueue实现的。
         */
        BlockingQueue<Integer> delayQueue = new DelayQueue();




        /**
         * 插入：add和offer作为插入方法的唯一不同就在于队列满了之后的处理方式。add抛出异常，而offer返回false。
         */

        blockingQueue.add(1);

        boolean offer1 = blockingQueue.offer(1);


        /**
         * 删除和获取元素
         *
         * 如果队列是空，remove和element方法会抛出异常，而poll和peek返回null。
         */
        Integer remove = blockingQueue.remove();//获取并移除队首的元素，该方法和poll方法的不同之处在于，如果队列为空该方法会抛出异常，而poll不会。
        Integer poll = blockingQueue.poll();//获取并移除队首的元素，如果队列为空，返回null。

        Integer element = blockingQueue.element();//获取队列首的元素，该方法和peek方法的不同之处在于，如果队列为空该方法会抛出异常，而peek不会。
        Integer peek = blockingQueue.peek();//获取队列首的元素，如果队列为空，返回null。


        /**
         * BlockingQueue新增的方法
         */
        try {
            blockingQueue.put(1);//向队尾插入元素。如果队列满了，阻塞等待，直到被中断为止。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            boolean offer = blockingQueue.offer(1, 1000, TimeUnit.MILLISECONDS);//向队尾插入元素。如果队列满了，阻塞等待timeout个时长，如果到了超时时间还没有空间，抛弃该元素。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Integer take = blockingQueue.take();//获取并移除队首的元素。如果队列为空，阻塞等待，直到被中断为止。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Integer poll1 = blockingQueue.poll(1000, TimeUnit.MILLISECONDS);//获取并移除队首的元素。如果队列为空，阻塞等待timeout个时长，如果到了超时时间还没有元素，则返回null。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        int i = blockingQueue.remainingCapacity();//返回在无阻塞的理想情况下（不存在内存或资源约束）此队列能接受的元素数量，如果该队列是无界队列，返回Integer.MAX_VALUE。

        int i1 = blockingQueue.drainTo(new ArrayList<>());//移除此队列中所有可用的元素，并将它们添加到给定 collection 中。

        int i2 = blockingQueue.drainTo(new ArrayList<>(), 10);//最多从此队列中移除给定数量的可用元素，并将这些元素添加到给定 collection 中。




    }


    public static void testDeque(){

        /**
         * addFirst(E e)：在前端插入元素，异常处理和add一样；
         * addLast(E e)：在后端插入元素，和add一样的效果；
         * offerFirst(E e)：在前端插入元素，异常处理和offer一样；
         * offerLast(E e)：在后端插入元素，和offer一样的效果；
         * removeFirst()：移除前端的一个元素，异常处理和remove一样；
         * removeLast()：移除后端的一个元素，和remove一样的效果；
         * pollFirst()：移除前端的一个元素，和poll一样的效果；
         * pollLast()：移除后端的一个元素，异常处理和poll一样；
         * getFirst()：获取前端的一个元素，和element一样的效果；
         * getLast()：获取后端的一个元素，异常处理和element一样；
         * peekFirst()：获取前端的一个元素，和peek一样的效果；
         * peekLast()：获取后端的一个元素，异常处理和peek一样；
         * removeFirstOccurrence(Object o)：从前端开始移除第一个是o的元素；
         * removeLastOccurrence(Object o)：从后端开始移除第一个是o的元素；
         * push(E e)：和addFirst一样的效果；
         * pop()：和removeFirst一样的效果。
         */

        Deque<Integer> deque = new LinkedBlockingDeque<>();

        /**
         * putFirst(E e)：在队首插入元素，如果队列满了，阻塞等待，直到被中断为止。
         * putLast(E e)：在队尾插入元素，如果队列满了，阻塞等待，直到被中断为止。
         * offerFirst(E e, long timeout, TimeUnit unit)：向队首插入元素。如果队列满了，阻塞等待timeout个时长，如果到了超时时间还没有空间，抛弃该元素。
         * offerLast(E e, long timeout, TimeUnit unit)：向队尾插入元素。如果队列满了，阻塞等待timeout个时长，如果到了超时时间还没有空间，抛弃该元素。
         * takeFirst()：获取并移除队首的元素。如果队列为空，阻塞等待，直到被中断为止。
         * takeLast()：获取并移除队尾的元素。如果队列为空，阻塞等待，直到被中断为止。
         * pollFirst(long timeout, TimeUnit unit)：获取并移除队首的元素。如果队列为空，阻塞等待timeout个时长，如果到了超时时间还没有元素，则返回null。
         * pollLast(long timeout, TimeUnit unit)：获取并移除队尾的元素。如果队列为空，阻塞等待timeout个时长，如果到了超时时间还没有元素，则返回null。
         * removeFirstOccurrence(Object o)：从队首开始移除第一个和o相等的元素。
         * removeLastOccurrence(Object o)：从队尾开始移除第一个和o相等的元素。
         */
        LinkedBlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void testTransferQueue(){

        TransferQueue transferQueue = new LinkedTransferQueue();
        boolean b = transferQueue.hasWaitingConsumer();
        int waitingConsumerCount = transferQueue.getWaitingConsumerCount();

        try {
            /**
             * tryTransfer(E e)：若当前存在一个正在等待获取的消费者线程（使用take()或者poll()函数），使用该方法会即刻转移/传输对象元素e并立即返回true；若不存在，则返回false，并且不进入队列。这是一个不阻塞的操作。
             */
            transferQueue.transfer(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        transferQueue.tryTransfer(1);//若当前存在一个正在等待获取的消费者线程，即立刻移交之；否则，会插入当前元素e到队列尾部，并且等待进入阻塞状态，到有消费者线程取走该元素。


        try {
            /**
             * 若当前存在一个正在等待获取的消费者线程，会立即传输给它;否则将插入元素e到队列尾部，并且等待被消费者线程获取消费掉；若在指定的时间内元素e无法被消费者线程获取，则返回false，同时该元素被移除。
             */
            transferQueue.tryTransfer(1,5000,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
