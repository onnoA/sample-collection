package com.onnoa.shop.common.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月26日 1:16
 */
public class ThreadClient {
    public static void main(String[] args) {
//        List list = new ArrayList<>();
//        list.add("Test1");
//        list.add("Test2");
//        list.add("Test3");
//        list.add("Test4");
//        list.add("Test5");
//        list.parallelStream().forEach(str-> System.out.println(str));
//        List list1 = Collections.synchronizedList(list);
//        list1.parallelStream().forEach(str-> System.out.println(str));


        for (int i = 0; i < 5; i++) {
            ThreadTest threadTest = new ThreadTest();
            Thread thread = new Thread(threadTest);
            thread.start();
        }

    }
}
