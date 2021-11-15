package com.onnoa.spring.configuration.metadata.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {

    public static final String PREFIX = "swagger";

    /**
     * 文档扫描包路径
     */
    private String basePackage = "";

    /**
     * title 如: 用户模块系统接口详情
     */
    private String title = "平台系统接口详情";

    /**
     * 服务文件介绍
     */
    private String description = "在线文档";

    /**
     * 服务条款网址
     */
    private String termsOfServiceUrl = "https://www.test.com/";

    /**
     * 版本
     */
    private String version = "V1.0";

}