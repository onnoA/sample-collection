package com.onnoa.shop.common.pattern.factory.abstractfactory.demo3;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月07日 15:43
 */
public class CustomTypeRecall implements RecallType {

    @Override
    public String recall(String type) {
        System.out.println("自定义召回方式。。。");
        return "自定义召回方式。。。";
    }
}
