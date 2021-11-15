package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public class YellowMaleHuman extends AbstractYellowHuman {

    @Override
    public String sex() {
        System.out.println("黄色人种男性");
        return "黄色人种男性";
    }
}
