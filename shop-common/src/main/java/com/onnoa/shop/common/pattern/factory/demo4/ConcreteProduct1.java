package com.onnoa.shop.common.pattern.factory.demo4;


public class ConcreteProduct1 extends Product {

    @Override
    public String absProduct() {
        System.out.println("创建的第一个具体的产品。。。。。。。。。");
        return "创建的第一个具体的产品。。。。。。。。。";
    }
}
