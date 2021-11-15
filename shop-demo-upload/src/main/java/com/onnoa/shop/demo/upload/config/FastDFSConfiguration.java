package com.onnoa.shop.demo.upload.config;

import com.onnoa.shop.demo.upload.factory.StorageFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastDFSConfiguration {
    @Bean
    public StorageFactory storageFactory() {
        return new StorageFactory();
    }
}
