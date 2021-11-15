package com.onnoa.shop.common.pattern.factory.demo2template;

public class Client {

    public static void main(String[] args) {
        Creator creator = new ProductCreator();
        // 生产产品1
        Product product1 = creator.createProduct(ConcreteProduct1.class);
        product1.absProduct();

        // 生产产品2
        Product product2 = creator.createProduct(ConcreteProduct2.class);
        product2.absProduct();

    }

}
