package com.onnoa.distributed.primary.key.util;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月29日 17:19
 */
public class Task implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName() + "   isDaemon:" + Thread.currentThread().isDaemon());
    }
}
