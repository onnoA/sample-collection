package com.onnoa.myshop.springcloud.provider.after.test;

import com.onnoa.shop.common.component.WebLogAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebLogAspect.class)
@EnableDiscoveryClient
@EnableFeignClients
public class ProviderAfterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderAfterApplication.class, args);
    }
}
