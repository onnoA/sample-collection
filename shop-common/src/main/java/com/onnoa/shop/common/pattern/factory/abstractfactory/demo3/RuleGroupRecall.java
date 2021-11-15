package com.onnoa.shop.common.pattern.factory.abstractfactory.demo3;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月07日 15:44
 */
public class RuleGroupRecall implements RecallType {

    @Override
    public String recall(String type) {
        System.out.println("规则组召回方式。。。");
        return "规则组召回方式。。。";
    }
}
