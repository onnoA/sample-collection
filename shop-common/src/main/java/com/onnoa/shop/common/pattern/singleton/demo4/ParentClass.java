package com.onnoa.shop.common.pattern.singleton.demo4;

/**
 * @author onnoA
 * @date 2021年05月22日 16:03
 */
public class ParentClass {
    static int num = 0;
    String name = "qqqqqq";
    static String name2 = "wwwwwwwwwww";
    static ParentClass parentClass = new ParentClass();

    // 3
    ParentClass(){
        System.out.println("这里是构造函数*************");
    }

     // 2
    {
        System.out.println("name1:" + name);
        System.out.println("这里是块1============");
    }

    // 1
    static {
        num += 1;
        System.out.println("parentClass.parentClass.parentClass.name:" + parentClass.name );
        System.out.println("这里是静态块*************" + num);
    }



    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ParentClass pa = new ParentClass();
        // 1.
        // 当在初始化类的时候，会先执行静态块和静态变量的声明。
        // 执行完静态块之后再执行非静态块。

        // 2.
        // 如果在类里声明了静态对象。像上面的例子。
        // 会先执行非静态块。然后按照(静态优先，非静态其次的原则进行。)
        // 静态块只会在非静态块执行完之后执行一次。

//        name1:qqqqqq
//这里是块1============
//这里是构造函数*************
//parentClass.parentClass.parentClass.name:qqqqqq
//这里是静态块*************1

//        =======================
//        name1:qqqqqq
//        这里是块1============
//        这里是构造函数*************
//        parentClass.parentClass.parentClass.name:qqqqqq
//        这里是静态块*************1
//        name1:qqqqqq
//        这里是块1============
//        这里是构造函数*************

        // parentClass.parentClass.parentClass.name:
        //这里是静态块*************1
        //name1:qqqqqq
        //这里是块1============
        //这里是构造函数*************
    }

}
