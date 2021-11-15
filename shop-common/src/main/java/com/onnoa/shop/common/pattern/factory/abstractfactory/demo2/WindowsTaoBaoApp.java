package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

/**
 * @author onnoA
 * @date 2021年05月14日 16:05
 */
public class WindowsTaoBaoApp extends AbstractWindows {

    @Override
    public String runningApp() {
        System.out.println("Windows 操作系统正在运行 TaoBao App 。。。。");
        return "Windows 操作系统正在运行 TaoBao App 。。。。";
    }
}
