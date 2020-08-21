package com.onnoa.shop.common.properties.base;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/6 12:35
 */
@Configuration
@EnableConfigurationProperties(value = ShopProperties.class)
public class ShopCoreConfig {
}
