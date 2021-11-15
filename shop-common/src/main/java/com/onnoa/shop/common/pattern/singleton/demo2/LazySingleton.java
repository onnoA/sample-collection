package com.onnoa.shop.common.pattern.singleton.demo2;

/**
 * @author onnoA 懒汉式单例模式
 * @date 2021年05月17日 17:26
 */
public class LazySingleton {

    private static LazySingleton LAZY_SINGLETON = null;

    private LazySingleton() {
    }

    //没有加入synchronized关键字的版本是线程不安全的
    public static LazySingleton getLazySingleton() {
        //判断当前单例是否已经存在，若存在则返回，不存在则再建立单例
        if (LAZY_SINGLETON == null) {
            LAZY_SINGLETON = new LazySingleton();
        }
        return LAZY_SINGLETON;
    }

}
