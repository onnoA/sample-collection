package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

public abstract class AbstractLinux implements OS {

    @Override
    public String welcomeScreen() {
        System.out.println("欢迎来到Linux的欢迎界面。。。。");
        return "欢迎来到Linux的欢迎界面。。。。";
    }
}
