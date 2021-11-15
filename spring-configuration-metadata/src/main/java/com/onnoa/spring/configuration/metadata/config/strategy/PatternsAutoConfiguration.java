package com.onnoa.spring.configuration.metadata.config.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class PatternsAutoConfiguration {

    @Bean
    public BusinessHandlerChoose businessHandlerChoose(List<BusinessProcessFactory> businessProcessFactoryList){
        BusinessHandlerChoose businessHandlerChoose = new BusinessHandlerChoose();
        businessHandlerChoose.setBusinessHandlerMap(businessProcessFactoryList);
        return businessHandlerChoose;
    }
}
