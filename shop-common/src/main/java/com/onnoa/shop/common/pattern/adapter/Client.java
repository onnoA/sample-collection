package com.onnoa.shop.common.pattern.adapter;

public class Client {

    public static void main(String[] args) {
        Adapter adapter = new Adapter();
        String s = adapter.powerOf5();
        System.out.println(s);
        String s1 = adapter.powerOf220();
        System.out.println(s1);
    }
}
