package com.onnoa.shop.common.pattern.singleton.demo4;

/**
 * @author onnoA 验证静态内部类的加载顺序
 * @date 2021年05月22日 15:32
 */
public class OuterClass {

    // 静态内部类
    public static class InnerClass {
        // 静态代码块
        static {
            System.out.println("加载内部类静态代码快。。。。");
        }

        // 静态方法
        static String staticInnerClassMethod(){
            System.out.println("加载静态方法。。。。。。。。");
            return "加载静态方法。。。。。。。。";
        }

    }

    static {
        System.out.println("加载外部类的静态代码块。。。。");
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();

        System.out.println("分隔符。。。。。。。。。。。。。。");

        OuterClass.InnerClass.staticInnerClassMethod();
    }
}
