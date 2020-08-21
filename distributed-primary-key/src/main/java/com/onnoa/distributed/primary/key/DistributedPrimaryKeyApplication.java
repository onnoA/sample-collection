package com.onnoa.distributed.primary.key;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.onnoa")
public class DistributedPrimaryKeyApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedPrimaryKeyApplication.class, args);
    }

}
