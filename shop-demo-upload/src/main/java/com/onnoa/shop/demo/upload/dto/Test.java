package com.onnoa.shop.demo.upload.dto;

public class Test {

    public static void main(String[] args) {
        helpEat(new GoldenDog());
    }

    public static void helpEat(Animal a) {
        // 然后再调用宠物本身的进食方法
        a.eat();	// 打印的结果
    }
}
