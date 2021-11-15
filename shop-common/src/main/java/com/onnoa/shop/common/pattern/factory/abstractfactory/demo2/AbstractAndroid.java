package com.onnoa.shop.common.pattern.factory.abstractfactory.demo2;

public abstract class AbstractAndroid implements OS {

    @Override
    public String welcomeScreen() {
        System.out.println("欢迎来到安卓的欢迎界面。。。。。");
        return "欢迎来到安卓的欢迎界面。。。。。";
    }
}
