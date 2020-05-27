package com.onnoa.shop.demo.elk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/26 17:49
 */
@Component
public class picUtil {
    @Value("${pic.url}")
    private static String requestUrl;

    public static String getRequestUrl(){
        return requestUrl;
    }
}
