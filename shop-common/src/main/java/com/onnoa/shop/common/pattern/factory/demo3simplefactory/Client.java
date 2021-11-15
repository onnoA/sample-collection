package com.onnoa.shop.common.pattern.factory.demo3simplefactory;


public class Client {

    public static void main(String[] args) {
        Product product1 = ProductCreator.createProduct(ConcreteProduct1.class);
        product1.absProduct();

        Product product2 = ProductCreator.createProduct(ConcreteProduct2.class);
        product2.absProduct();
    }

}
