package com.onnoa.shop.common.pattern.factory.demo1;

public class WhiteHuman implements Human {

    @Override
    public String color() {
        System.out.println("白种人，皮肤是白色的。。。。。");
        return "白种人，皮肤是白色的。。。。。";
    }

    @Override
    public String language() {
        System.out.println("白种人，语言是欧式英语。。。。。");
        return "白种人，语言是欧式英语。。。。。";
    }
}
