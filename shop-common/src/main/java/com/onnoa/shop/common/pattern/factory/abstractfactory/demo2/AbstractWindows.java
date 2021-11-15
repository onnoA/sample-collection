package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

public abstract class AbstractWindows implements OS {

    @Override
    public String welcomeScreen() {
        System.out.println("欢迎来到 windows 操作系统的欢迎界面。。。。");
        return "windows 操作系统的欢迎界面";
    }


}
