package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

/**
 * @author onnoA
 * @date 2021年05月14日 16:10
 */
public class AndroidTaoBaoApp extends AbstractAndroid {
    @Override
    public String runningApp() {
        System.out.println("Android 操作系统正在运行 TaoBao App 。。。。");
        return "Android 操作系统正在运行 TaoBao App 。。。。";
    }
}
