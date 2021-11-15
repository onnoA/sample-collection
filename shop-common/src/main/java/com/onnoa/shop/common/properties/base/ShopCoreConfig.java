package com.onnoa.shop.common.properties.base;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/6 12:35
 */
@Configuration
// 1.
// @EnableConfigurationProperties 注解， 使使用 @ConfigurationProperties 注解的类生效。(ShopProperties)
//@EnableConfigurationProperties(value = ShopProperties.class)

// 2.
//  或者 ShopProperties 使用@Component注解
public class ShopCoreConfig {
}
