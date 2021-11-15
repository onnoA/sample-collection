package com.onnoa.shop.common.pattern.factory.abstractfactory.demo3;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月07日 15:43
 */
public class OfflineRecall implements RecallType {

    @Override
    public String recall(String type) {
        System.out.println("离线召回方式。。。。");
        return "离线召回方式。。。。";
    }
}
