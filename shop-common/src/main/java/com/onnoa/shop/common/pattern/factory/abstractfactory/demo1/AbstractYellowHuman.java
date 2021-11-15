package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public abstract class AbstractYellowHuman implements Human {

    @Override
    public String color() {
        System.out.println("黄种人颜色是黄色的");
        return "黄种人颜色是黄色的";
    }

    @Override
    public String language() {
        System.out.println("黄种人的语言是英式英语");
        return "黄种人的语言是中文";
    }


}
