package com.onnoa.shop.common.pattern.singleton.demo4;

/**
 * @author onnoA
 * @date 2021年05月22日 15:35
 */
public class Client {

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();

        System.out.println("分隔符。。。。。。。。。。。。。。");

        OuterClass.InnerClass.staticInnerClassMethod();
    }
}
