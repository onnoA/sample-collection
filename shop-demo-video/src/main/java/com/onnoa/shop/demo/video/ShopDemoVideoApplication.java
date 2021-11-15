package com.onnoa.shop.demo.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.onnoa.shop")
//@ComponentScan(basePackages = {"com.onnoa.shop"})
@MapperScan( basePackages = {"com.onnoa.shop.demo.video.mapper"})
//@EnableTransactionManagement
@ImportResource(locations = {"classpath:/spring/applicationContext*.xml"})
@EnableTransactionManagement
public class ShopDemoVideoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDemoVideoApplication.class, args);
    }

}
