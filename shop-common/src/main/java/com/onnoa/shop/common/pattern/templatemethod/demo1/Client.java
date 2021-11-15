package com.onnoa.shop.common.pattern.templatemethod.demo1;

/**
 * @author onnoA
 * @date 2021年05月27日 19:54
 */
public class Client {

    public static void main(String[] args) {
        AbstractClass concreteClass1 = new ConcreteClass1();
        concreteClass1.methodTemplate();

        System.out.println("==========================分割线===================================");

        AbstractClass concreteClass2 = new ConcreteClass2();
        concreteClass2.methodTemplate();


    }
}
