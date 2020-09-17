package com.onnoa.shop.demo.authority.system;

import com.onnoa.shop.common.component.SqlLogInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/5 22:18
 */
@SpringBootApplication(scanBasePackages = "com.onnoa.shop")
@ComponentScan(basePackages = {
    "com.onnoa.shop"
})
//@Import(SqlLogInterceptor.class)
@ImportAutoConfiguration({SqlLogInterceptor.class})
@MapperScan("com.onnoa.shop.demo.authority.system.mapper")
@EnableAsync
public class ShopDemoAuthoritySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopDemoAuthoritySystemApplication.class, args);
    }

}
