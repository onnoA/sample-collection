package com.onnoa.shop.common.pattern.singleton.demo4;

/**
 * @author onnoA 静态内部类\登记式单例模式
 * @date 2021年05月22日 15:14
 */
public class StaticInnerClassSingleton {

    private static class SingletonHolder {
        private static final StaticInnerClassSingleton STATIC_INNER_CLASS_SINGLETON = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton() {

    }

    public static final StaticInnerClassSingleton getInstance() {
        return SingletonHolder.STATIC_INNER_CLASS_SINGLETON;
    }
}
