package com.onnoa.shop.demo.comment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.onnoa.shop")
@ComponentScan(basePackages = {"com.onnoa.shop"})
@MapperScan("com.onnoa.shop.demo.comment.mapper")
public class ShopDemoCommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDemoCommentApplication.class, args);
    }

}
