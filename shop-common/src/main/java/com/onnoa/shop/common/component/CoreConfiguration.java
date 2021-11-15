package com.onnoa.shop.common.component;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class CoreConfiguration {
    @Bean
    public SqlLogInterceptor sqlLogInterceptor() {
        return new SqlLogInterceptor();
    }
}
