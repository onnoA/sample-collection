package com.onnoa.shop.common.pattern.builder.demo1;

/**
 * @author onnoA
 * @date 2021年06月01日 15:47
 */
public class ConcretePro1 extends Product {

    @Override
    protected void step1() {
        System.out.println("ConcretePro1 经历步骤1.....");
    }

    @Override
    protected void step2() {
        System.out.println("ConcretePro1 经历步骤2.....");
    }
}
