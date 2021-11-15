package com.onnoa.shop.common.pattern.adapter;

/**
 * 适配者
 */
public class Adapter extends Adaptee implements Target {

    @Override
    public String powerOf5() {
        return "5V";
    }
}
