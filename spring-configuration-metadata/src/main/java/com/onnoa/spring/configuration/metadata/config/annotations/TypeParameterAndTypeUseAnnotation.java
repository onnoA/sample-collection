package com.onnoa.spring.configuration.metadata.config.annotations;

import java.util.ArrayList;

public class TypeParameterAndTypeUseAnnotation<@NotEmpty T> {

    //使用TYPE_PARAMETER类型，会编译不通过
//    public @NotEmpty T test(@NotEmpty T a){
//        new ArrayList<@NotEmpty String>().add(a);
//            return a;
//    }

    //使用TYPE_USE类型，编译通过
    public @NotNull T test1(@NotNull T a){
        new ArrayList<@NotNull String>().add(String.valueOf(a));
        return a;
    }

    //泛型类型声明时，使用TYPE_USE类型，编译通过
    class A <@NotNull TT>{

        private String str = null;
    }

    public static void main(String[] args) {

    }
}
