package com.onnoa.shop.common.pattern.builder.demo1;

import java.util.List;

/**
 * @author onnoA
 * @date 2021年06月01日 15:52
 */
public class ConcretePro1Builder extends ProductBuilder {

    private ConcretePro1 concretePro1 = new ConcretePro1();

    @Override
    public void setSeq(List<String> sequence) {
        this.concretePro1.setSeq(sequence);
    }

    @Override
    public Product getProduct() {
        return this.concretePro1;
    }
}
