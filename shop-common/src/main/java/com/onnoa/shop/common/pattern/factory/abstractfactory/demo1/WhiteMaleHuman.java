package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public class WhiteMaleHuman extends AbstractWhiteHuman {


    @Override
    public String sex() {
        System.out.println("白人男性");
        return "白人男性";
    }
}
