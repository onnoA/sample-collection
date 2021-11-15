package com.onnoa.spring.configuration.metadata.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
// ElementType.TYPE_USE:JavaSE8引进，此类型包括类型声明和类型参数声明，是为了方便设计者进行类型检查，
// 例如，如果使用@Target（ElementType.TYPE_USE）对@NonNull进行标记，则类型检查器可以将@NonNull class C {...} C类的所有变量都视为非null
@Target(ElementType.TYPE_USE)
public @interface NotNull {

}
