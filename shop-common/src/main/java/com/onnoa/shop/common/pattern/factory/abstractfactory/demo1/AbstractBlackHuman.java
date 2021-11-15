package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;


public abstract class AbstractBlackHuman implements Human {

    public String color() {
        System.out.println("黑人的颜色是黑色的。。。。。。。。。。。。。。");
        return "黑人的颜色是黑色的。。。。。。。。。。。。。。";
    }

    public String language() {
        System.out.println("黑人的语言是英文。。。。。。。。。。。。。。");
        return "黑人的语言是英文。。。。。。。。。。。。。。";
    }

}
