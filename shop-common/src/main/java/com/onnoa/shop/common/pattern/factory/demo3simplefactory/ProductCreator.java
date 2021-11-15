package com.onnoa.shop.common.pattern.factory.demo3simplefactory;


public class ProductCreator {

    public static <T extends Product> T createProduct(Class<T> clazz) {
        Product product = null;
        try {
            // 返回的是全类名：clazz.getName() =》  com.onnoa.shop.common.pattern.factory.demo2.ConcreteProduct1
            product = (T) Class.forName(clazz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) product;
    }
}
