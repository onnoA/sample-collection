package com.onnoa.spring.configuration.metadata.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
// ElementType.TYPE_PARAMETER:类型参数声明，JavaSE8引进，可以应用于类的泛型声明之处
@Target(ElementType.TYPE_PARAMETER)
public @interface NotEmpty {

}
