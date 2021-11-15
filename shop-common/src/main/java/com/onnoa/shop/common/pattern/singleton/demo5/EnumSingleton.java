package com.onnoa.shop.common.pattern.singleton.demo5;

/**
 *  枚举单例模式 (在effective java中说道，最佳的单例实现模式就是枚举模式。利用枚举的特性，让JVM来帮我们保证线程安全和单一实例的问题。除此之外，写法还特别简单。)
 * @author onnoA
 * @date 2021年05月26日 15:14
 */
public enum  EnumSingleton {
    INSTANCE;

    public String doSomething(){
        System.out.println("doSomething 方法");
        return "doSomething 方法";
    }

}
