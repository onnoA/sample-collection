package com.onnoa.distributed.primary.key.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月29日 17:20
 */
public class ThreadFactoryDemo implements ThreadFactory {
    private int id = 0;

    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName("Thread-Name:" + id++);
        t.setDaemon(true);
        return t;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exe = new ThreadPoolExecutor(5, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactoryDemo());
        for (int i = 0; i < 5; i++) {
            exe.execute(new Task());
        }
        exe.shutdown();
        TimeUnit.MILLISECONDS.sleep(1000);
    }
}