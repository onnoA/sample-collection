package com.onnoa.shop.common.pattern.builder.demo1;

/**
 * @author onnoA
 * @date 2021年06月01日 16:15
 */
public class Client {

    public static void main(String[] args) {
        // 导演类
        Director director = new Director();
        director.getConcretePro1().build();

    }
}
