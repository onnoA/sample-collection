package com.onnoa.shop.common.pattern.singleton.demo1;

/**
 * @author onnoA
 * @date 2021年05月14日 17:02
 */
public class Client {

    public static void main(String[] args) {
        EagerSingleton singleTon = EagerSingleton.getSingleton();
        System.out.println("获取到的单例对象:" + singleTon);
    }


}
