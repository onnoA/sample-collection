package com.onnoa.shop.demo.upload;

import com.onnoa.shop.common.component.TestComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
//@ImportAutoConfiguration({TestComponent.class})
@Import(TestComponent.class)
public class UploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class, args);
    }

}
