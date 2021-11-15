package com.onnoa.shop.common.pattern.factory.demo2template;

public abstract class Creator {

    public abstract <T extends Product> T createProduct(Class<T> clazz);
}
