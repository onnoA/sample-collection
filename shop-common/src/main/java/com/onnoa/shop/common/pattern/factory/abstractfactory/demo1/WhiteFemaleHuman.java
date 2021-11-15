package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public class WhiteFemaleHuman extends AbstractWhiteHuman {

    @Override
    public String sex() {
        System.out.println("白人女性");
        return "白人女性";
    }
}
