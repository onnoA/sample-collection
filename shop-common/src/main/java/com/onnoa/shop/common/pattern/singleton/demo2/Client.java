package com.onnoa.shop.common.pattern.singleton.demo2;

/**
 * @author onnoA
 * @date 2021年05月17日 17:31
 */
public class Client {

    public static void main(String[] args) {
        LazySingleton lazySingleton = LazySingleton.getLazySingleton();
        System.out.println("获取到的单例对象:" + lazySingleton);

    }
}
