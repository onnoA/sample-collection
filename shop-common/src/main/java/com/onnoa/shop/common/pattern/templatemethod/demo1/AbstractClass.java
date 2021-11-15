package com.onnoa.shop.common.pattern.templatemethod.demo1;

/**
 * @author onnoA
 * @date 2021年05月27日 19:50
 */
public abstract class AbstractClass {

    // 基本方法
    protected abstract void doSomething();

    // 基本方法
    protected abstract void doAnything();

    // 模板方法
    public final void methodTemplate(){
        //调用基本方法，完成相关逻辑

        this.doSomething();


        this.doAnything();;
    }
}
