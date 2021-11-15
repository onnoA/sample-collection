package com.onnoa.shop.common.pattern.templatemethod.demo1;

/**
 * @author onnoA
 * @date 2021年05月27日 19:50
 */
public class ConcreteClass1 extends AbstractClass {

    @Override
    protected void doSomething() {
        System.out.println("执行 ConcreteClass1 的 doSomething。。。。 ");

    }

    @Override
    protected void doAnything() {

        System.out.println("执行 ConcreteClass1 的 doAnything。。。。"
        );

    }
}
