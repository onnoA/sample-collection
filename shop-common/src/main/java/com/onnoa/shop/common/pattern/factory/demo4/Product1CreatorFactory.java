package com.onnoa.shop.common.pattern.factory.demo4;


public class Product1CreatorFactory extends AbstractCreatorFactory {

    public Product createProduct() {
        return new ConcreteProduct1();
    }
}
