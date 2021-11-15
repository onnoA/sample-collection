package com.onnoa.shop.common.pattern.factory.demo2template;

public class ProductCreator extends Creator {

    @Override
    public <T extends Product> T createProduct(Class<T> clazz) {
        try {
            // 返回的是全类名：clazz.getName() =》  com.onnoa.shop.common.pattern.factory.demo2.ConcreteProduct1
            return (T) Class.forName(clazz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
