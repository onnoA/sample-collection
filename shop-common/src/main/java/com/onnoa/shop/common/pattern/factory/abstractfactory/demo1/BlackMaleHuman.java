package com.onnoa.shop.common.pattern.factory.abstractfactory.demo1;

public class BlackMaleHuman extends AbstractBlackHuman {

    @Override
    public String sex() {
        System.out.println("黑人男性。。。。。。。。。。。。");
        return "黑人男性。。。。。。。。。。。。";
    }
}
