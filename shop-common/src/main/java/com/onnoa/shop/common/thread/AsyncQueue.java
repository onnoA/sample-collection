package com.onnoa.shop.common.thread;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月23日 15:56
 */
public class AsyncQueue {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<List<String>> actionRecieveQueue = new LinkedBlockingQueue<>(100);
//        actionRecieveQueue.put("test");
//        actionRecieveQueue.take();
        List<String> actionRecieveList = new ArrayList<>(510);
        actionRecieveQueue.drainTo(Collections.singleton(actionRecieveList));


    }


}
