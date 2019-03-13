package com.wyq.firehelper.java.concurrent;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Uni.W
 * @date 2019/3/12 19:12
 */
public class Executor {

    public static void main(String[] args) {

        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 1;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        BlockingDeque<Runnable> taskQueue = new LinkedBlockingDeque<>();

        final ThreadFactory sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Task #" + mCount.getAndIncrement());
            }
        };
        ExecutorService executorService = new ThreadPoolExecutor(NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, taskQueue, sThreadFactory);

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {

                String s = "hello ";
                Thread.sleep(5000);
                s = s + "word";
                return s;
            }
        });


    }
}
