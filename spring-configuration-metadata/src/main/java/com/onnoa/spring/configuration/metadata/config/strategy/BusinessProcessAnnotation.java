package com.onnoa.spring.configuration.metadata.config.strategy;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;


// 可以被doc类似工具文档化
@Documented
//作用于类、接口、枚举
@Target(ElementType.TYPE)
// 程序启动即加载
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface BusinessProcessAnnotation {

    /**
     * 类型
     * @return
     */
    String type();

    /**
     * key
     * @return
     */
    String source();

}
