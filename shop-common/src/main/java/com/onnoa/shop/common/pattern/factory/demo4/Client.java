package com.onnoa.shop.common.pattern.factory.demo4;


public class Client {

    public static void main(String[] args) {

        Product product1 = new Product1CreatorFactory().createProduct();
        product1.absProduct();

        Product product2 = new Product2CreatorFactory().createProduct();
        product2.absProduct();

    }

}
