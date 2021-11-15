package com.onnoa.spring.configuration.metadata.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class TestTypeUse {


    @Target(ElementType.TYPE_PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeParameterAnnotation {

    }

    // 如下是该注解的使用例子
    public class TypeParameterClass<@TypeParameterAnnotation T> {
        public <@TypeParameterAnnotation U> T foo(T t) {
            return null;
        }
    }



    @Target(ElementType.TYPE_USE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TypeUseAnnotation {

    }

    public static @TypeUseAnnotation class TypeUseClass<@TypeUseAnnotation T> extends @TypeUseAnnotation Object {
        public void foo(@TypeUseAnnotation T t) throws @TypeUseAnnotation Exception {

        }
    }

    // 如下注解的使用都是合法的
    @SuppressWarnings({ "rawtypes", "unused", "resource" })
    public static void main(String[] args) throws Exception {
        TypeUseClass<@TypeUseAnnotation String> typeUseClass = new @TypeUseAnnotation TypeUseClass<>();
        typeUseClass.foo("");
        List<@TypeUseAnnotation Comparable> list1 = new ArrayList<>();
        List<? extends Comparable> list2 = new ArrayList<@TypeUseAnnotation Comparable>();
//        @TypeUseAnnotation String text = (@TypeUseAnnotation String)new Object();
        java.util. @TypeUseAnnotation Scanner console = new java.util.@TypeUseAnnotation Scanner(System.in);


    }

}
