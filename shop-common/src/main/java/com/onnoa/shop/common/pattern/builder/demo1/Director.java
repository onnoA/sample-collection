package com.onnoa.shop.common.pattern.builder.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author onnoA
 * @date 2021年06月01日 16:11
 */
public class Director {

    private List<String> sequence = new ArrayList<>();

    private ConcretePro1Builder pro1Builder = new ConcretePro1Builder();

    private ProductBuilder productBuilder;

//    public Director(ProductBuilder productBuilder){
//        this.productBuilder =  productBuilder;
//    }

    public ConcretePro1 getConcretePro1() {
        sequence.clear();
        sequence.add("step1");
        sequence.add("step2");
        ConcretePro1Builder pro1Builder = this.pro1Builder;
        pro1Builder.setSeq(sequence);
        return (ConcretePro1) pro1Builder.getProduct();
    }

}
