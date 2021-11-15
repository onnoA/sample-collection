package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

/**
 * @author onnoA
 * @date 2021年05月14日 16:09
 */
public class LinuxTaoBaoApp extends AbstractLinux {

    @Override
    public String runningApp() {
        System.out.println("Linux 操作系统正在运行 TaoBao App 。。。。");
        return "Linux 操作系统正在运行 TaoBao App 。。。。";
    }
}
