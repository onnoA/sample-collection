package com.onnoa.shop.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpConfig {

    /**
     * 接口调用请求超时
     */
    //@Value("${http.client.connectTimeout}")
    private Integer connectTimeout;

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }
}