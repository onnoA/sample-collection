package com.onnoa.spring.configuration.metadata.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@ComponentScan(basePackages = {"com.onnoa.spring.configuration.metadata.config"})
public class SpringConfigurationMetadataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringConfigurationMetadataApplication.class, args);
    }

}
