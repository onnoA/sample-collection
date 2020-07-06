package com.onnoa.shop.demo.authority.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/5 22:18
 */
@SpringBootApplication(scanBasePackages = "com.onnoa.shop")
@ComponentScan(basePackages = {"com.onnoa.shop"})
@MapperScan("com.onnoa.shop.demo.authority.system.mapper")
public class ShopDemoAuthoritySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDemoAuthoritySystemApplication.class, args);
    }

}
