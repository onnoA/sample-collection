package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public class YellowFemaleHuman extends AbstractYellowHuman {

    @Override
    public String sex() {
        System.out.println("黄色人种女性");
        return "黄色人种女性";
    }
}
