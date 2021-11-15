package com.onnoa.shop.common.thread;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月23日 16:52
 */
public class BlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);
        BlockingQueue<List<String>> queueList = new LinkedBlockingQueue<List<String>>(10);

        Producer producer1 = new Producer(queue,queueList);
        Producer producer2 = new Producer(queue,queueList);
        Producer producer3 = new Producer(queue,queueList);
        Consumer consumer = new Consumer(queue,queueList);

        // 借助Executors
        ExecutorService service = Executors.newCachedThreadPool();
        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);

        // 执行10s
        Thread.sleep(10 * 1000);

        queue.put("测试能否拿到数据");
        producer1.stop();
        producer2.stop();
        producer3.stop();

        Thread.sleep(2000);
        // 退出Executor
        service.shutdown();
    }
}
