package com.onnoa.shop.common.thread;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月23日 16:53
 */
public class Producer implements Runnable  {

    public Producer(BlockingQueue queue, BlockingQueue<List<String>> queueList) {
        this.queue = queue;
        this.queueList = queueList;
    }

    public void run() {
        String data = null;
        Random r = new Random();

        System.out.println("启动生产者线程！");
        try {
            while (isRunning) {
                System.out.println("正在生产数据...");
                Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));

                data = "data:" + count.incrementAndGet();
                System.out.println("将数据：" + data + "放入队列...");
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("放入数据失败：" + data);
                }
                List<String> list = new ArrayList<>();
                list.add(data);
                System.out.println("生产的集合"+ JSON.toJSON(list));
                queueList.put(list);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("退出生产者线程！");
        }
    }

    public void stop() {
        isRunning = false;
    }

    private volatile boolean      isRunning               = true;
    private BlockingQueue queue;
    private BlockingQueue<List<String>> queueList;
    private static AtomicInteger  count                   = new AtomicInteger();
    private static final int      DEFAULT_RANGE_FOR_SLEEP = 1000;
}
