package com.onnoa.shop.common.pattern.singleton.demo1;

/**
 * @author onnoA 饿汉式单例模式
 * @date 2021年05月14日 16:41
 */
public class EagerSingleton {

    private static final EagerSingleton SINGLETON = new EagerSingleton();

    // 限制产生多个对象: Singleton类只有一个构造方法并且是被private修饰的，所以用户无法通过new方法创建该对象实例
    private EagerSingleton() {

    }

    // 对象只能通过该方法获取Singleton实例对象
    public static EagerSingleton getSingleton() {
        return SINGLETON;
    }
}
