package com.onnoa.shop.demo.elk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.onnoa.shop.**"})
public class ShopDemoElkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDemoElkApplication.class, args);
    }

}
