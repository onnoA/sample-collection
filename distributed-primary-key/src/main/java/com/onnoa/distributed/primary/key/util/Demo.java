package com.onnoa.distributed.primary.key.util;

import java.util.concurrent.TimeUnit;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月29日 17:27
 */
public class Demo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
//            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("----睡眠一秒-----");
//            }
        });
        //默认为false,设置为false代表非守护线程,true为守护线程,守护线程在主方法结束时候结束
        thread.setDaemon(true);
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程over");
    }
}
