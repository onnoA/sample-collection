package com.onnoa.shop.common.pattern.builder.demo1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author onnoA
 * @date 2021年06月01日 15:46
 */
public abstract class Product {

    // 根据改列表记录方法执行的顺序
    private List<String> sequence = new ArrayList<>();

    // 步骤 1
    protected abstract void step1();

    // 步骤 2
    protected abstract void step2();

    // 假设 product 对象的构建有以上的 step1、step2 步骤，
    public final void build() {
        for (int i = 0; i < sequence.size(); i++) {
            String seq = this.sequence.get(i);
            if ("step1".equals(seq)) {
                this.step1();
            } else if ("step2".equals(seq)) {
                this.step2();
            }
        }
    }

    public final void setSeq(List<String> sequence) {
        this.sequence = sequence;
    }
}
