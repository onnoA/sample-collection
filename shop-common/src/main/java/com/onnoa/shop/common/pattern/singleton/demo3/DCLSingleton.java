package com.onnoa.shop.common.pattern.singleton.demo3;

/**
 * @author onnoA 双重校验锁(DCL)
 * @date 2021年05月22日 14:59
 */
public class DCLSingleton {

    private volatile static DCLSingleton singleton;

    public static DCLSingleton getSingleton() {
        synchronized (DCLSingleton.class) {
            if (singleton == null) {
                singleton = new DCLSingleton();
            }
        }
        return singleton;
    }
}
