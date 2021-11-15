package com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例;

/**
 * @className: SpecificProduct2
 * @description:
 * @author: onnoA
 * @date: 2021/9/15
 **/
public class XiaoMiPhone implements IPhoneProduct {

    @Override
    public String phoneName() {
        return "这是小米产品.。。。。。。。。。。。。";
    }
}
