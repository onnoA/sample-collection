package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

/**
 * @author onnoA
 * @date 2021年05月14日 16:18
 */
public class LinuxJDApp extends AbstractLinux {
    @Override
    public String runningApp() {
        System.out.println("Linux 操作系统正在运行京东 App 。。。。");
        return "Linux 操作系统正在运行京东 App 。。。。";
    }
}
