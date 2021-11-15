package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public class BlackFemaleHuman extends AbstractBlackHuman {

    @Override
    public String sex() {
        System.out.println("黑人女性。。。。。。。。。。。。");
        return "黑人女性。。。。。。。。。。。。";
    }
}
