package com.onnoa.shop.common.thread;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月23日 16:52
 */
public class Consumer implements Runnable {
    public Consumer(BlockingQueue<String> queue, BlockingQueue queueList) {
        this.queue = queue;
        this.queueList = queueList;
    }

    public void run() {
        System.out.println("启动消费者线程！");
        Random r = new Random();
        boolean isRunning = true;
        try {
            while (isRunning) {
                System.out.println("正从队列获取数据...");
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    System.out.println("拿到数据：" + data);
                    System.out.println("正在消费数据：" + data);
                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                } else {
                    // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                    isRunning = false;
                }

                queueList.drainTo(list);
                if(list.size() >= 3){
                    System.out.println("批量消费消息。。" + JSON.toJSON(list));
                    list.clear();
                } else {
                    System.out.println("==========" + JSON.toJSON(list));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("退出消费者线程！");
        }
    }

    private BlockingQueue<String> queue;
    private BlockingQueue<String> queueList;
    private static final int      DEFAULT_RANGE_FOR_SLEEP = 1000;
    ArrayList<String> list = new ArrayList<>();

}
