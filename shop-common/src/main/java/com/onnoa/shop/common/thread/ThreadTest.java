package com.onnoa.shop.common.thread;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月17日 19:40
 */
public class ThreadTest implements Runnable {

    @SneakyThrows
    @Override
    public void run() {
//        long start = System.currentTimeMillis();
//        System.out.println("开启线程。。。");
//        Thread.sleep(10000);
//        long end = System.currentTimeMillis();
//        System.out.println("线程名称："+Thread.currentThread().getName() + "结束时间:" + end + "花费时间"+ (end - start));

        List list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i + "");
        }
//        list.add("Test1");
//        list.add("Test2");
//        list.add("Test3");
//        list.add("Test4");
//        list.add("Test5");
        list.parallelStream().forEach(str-> System.out.println(str));
//        List list1 = Collections.synchronizedList(list);
//        list1.parallelStream().forEach(str-> System.out.println(str));
    }
}
