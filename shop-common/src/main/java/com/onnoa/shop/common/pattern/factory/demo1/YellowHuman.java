package com.onnoa.shop.common.pattern.factory.demo1;

public class YellowHuman implements Human {

    @Override
    public String color() {
        System.out.println("黄种人，皮肤是黄色的。。。。。");
        return "黄种人，皮肤是黄色的。。。。。";
    }

    @Override
    public String language() {
        System.out.println("黄种人，语言是中文。。。。。");
        return "黄种人，语言是中文。。。。。";
    }
}
