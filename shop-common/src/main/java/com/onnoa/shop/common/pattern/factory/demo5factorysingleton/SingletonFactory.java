package com.onnoa.shop.common.pattern.factory.demo5factorysingleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonFactory {

    private static Singleton singleton;

    static {
        try {
            Class<?> clazz = Class.forName(Singleton.class.getName());
            // 获取无参构造
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            singleton = (Singleton) constructor.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static Singleton getSingleton() {
        return singleton;
    }

}
