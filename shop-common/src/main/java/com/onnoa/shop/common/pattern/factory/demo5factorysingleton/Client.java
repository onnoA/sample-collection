package com.onnoa.shop.common.pattern.factory.demo5factorysingleton;

public class Client {

    public static void main(String[] args) {
        Singleton singleton = SingletonFactory.getSingleton();
        System.out.println("获取到 singleton 对象" + singleton);
    }
}
