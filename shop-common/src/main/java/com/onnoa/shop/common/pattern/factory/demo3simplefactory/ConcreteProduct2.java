package com.onnoa.shop.common.pattern.factory.demo3simplefactory;


public class ConcreteProduct2 extends Product {

    @Override
    public String absProduct() {
        System.out.println("创建的第二个具体的产品。。。。。。。。。");
        return "创建的第二个具体的产品。。。。。。。。。";
    }
}
