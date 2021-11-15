package com.onnoa.shop.common.pattern.factory.demo4;

public class Product2CreatorFactory extends AbstractCreatorFactory {

    public Product createProduct() {
        return new ConcreteProduct2();
    }
}
