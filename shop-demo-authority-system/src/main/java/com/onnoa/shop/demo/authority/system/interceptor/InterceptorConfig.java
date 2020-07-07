package com.onnoa.shop.demo.authority.system.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessTokenInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求
    }

    @Bean
    public AccessTokenInterceptor accessTokenInterceptor() {
        return new AccessTokenInterceptor();
    }
}
