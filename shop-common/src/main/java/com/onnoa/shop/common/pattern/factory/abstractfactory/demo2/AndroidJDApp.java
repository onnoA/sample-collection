package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

/**
 * @author onnoA
 * @date 2021年05月14日 16:20
 */
public class AndroidJDApp extends AbstractAndroid {

    @Override
    public String runningApp() {
        System.out.println("Android 操作系统正在运行京东 App 。。。。");
        return "Android 操作系统正在运行京东 App 。。。。";
    }
}
