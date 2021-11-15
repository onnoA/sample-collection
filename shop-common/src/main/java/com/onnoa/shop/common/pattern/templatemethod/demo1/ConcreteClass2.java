package com.onnoa.shop.common.pattern.templatemethod.demo1;

/**
 * @author onnoA
 * @date 2021年05月27日 19:51
 */
public class ConcreteClass2 extends AbstractClass {

    @Override
    protected void doSomething() {
        // 执行业务
        System.out.println("执行 ConcreteClass2 的 doSomething。。。。 ");
    }

    @Override
    protected void doAnything() {
        System.out.println("执行 ConcreteClass2 的 doAnything。。。。 ");
    }
}
