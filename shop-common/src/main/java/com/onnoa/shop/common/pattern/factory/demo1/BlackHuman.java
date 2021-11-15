package com.onnoa.shop.common.pattern.factory.demo1;

public class BlackHuman extends YellowHuman implements Human {

    @Override
    public String color() {
        System.out.println("黑种人，皮肤是黑色的。。。。。");
        return "黑种人，皮肤是黑色的。。。。。";
    }

    @Override
    public String language() {
        System.out.println("黑种人，语言是美式英语。。。。。");
        return "黑种人，语言是美式英语。。。。。";
    }
}
