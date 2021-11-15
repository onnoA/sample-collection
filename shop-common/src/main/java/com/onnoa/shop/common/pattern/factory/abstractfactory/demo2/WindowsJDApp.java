package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

/**
 * @author onnoA
 * @date 2021年05月14日 16:19
 */
public class WindowsJDApp extends AbstractWindows {

    @Override
    public String runningApp() {
        System.out.println("Windows 操作系统正在运行京东 App 。。。。");
        return "Windows 操作系统正在运行京东 App 。。。。";
    }
}
