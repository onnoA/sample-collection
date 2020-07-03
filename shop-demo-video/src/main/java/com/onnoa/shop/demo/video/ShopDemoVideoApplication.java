package com.onnoa.shop.demo.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.onnoa.shop")
@ComponentScan(basePackages = {"com.onnoa.shop"})
@MapperScan("com.onnoa.shop.demo.video.mapper")
public class ShopDemoVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDemoVideoApplication.class, args);
    }

}
