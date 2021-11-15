package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public abstract class AbstractWhiteHuman implements Human {

    @Override
    public String color() {
        System.out.println("白色人种颜色是白色的");
        return "白色人种";
    }

    @Override
    public String language() {
        System.out.println("白种人语言是英式英语");
        return "白种人语言是英式英语";
    }
}
