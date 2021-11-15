package com.onnoa.shop.common.pattern.builder.demo1;

import java.util.List;

/**
 * @author onnoA
 * @date 2021年06月01日 15:50
 */
public abstract class ProductBuilder {

    // 构建 product 的顺序
    public abstract void setSeq(List<String> sequence);

    // 获取 product 对象
    public abstract Product getProduct();


}
