package com.onnoa.myshop.springcloud.provider.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
// @MapperScan("com.onnoa.shop.demo.authority.system.mapper")
@EnableAsync
// @EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients
public class MyshopSpringCloudProviderTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyshopSpringCloudProviderTestApplication.class, args);
    }
}
