package com.onnoa.shop.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * 配置文件
 */
@Configuration
public class PropertyHolderConfiguration implements ApplicationContextAware {
    private static ApplicationContext context = null;

    @Bean
    public PropertyHolder getPropertyHolder() throws IOException {
        PropertyHolder propertyHolder = new PropertyHolder();
        propertyHolder.setSystemPropertiesModeName("SYSTEM_PROPERTIES_MODE_OVERRIDE");
        propertyHolder.setIgnoreUnresolvablePlaceholders(true);
        propertyHolder.setIgnoreResourceNotFound(true);
        propertyHolder.setFileEncoding("utf-8");
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Environment environment = context.getEnvironment();
        String profile = context.getEnvironment().getActiveProfiles()[0];
        Resource resourceEnv;
        if (profile.equals("dev")) {
            resourceEnv = resolver.getResource("classpath:application-dev.yml");
        } else if (profile.equals("test")) {
            resourceEnv = resolver.getResource("classpath:application-test.yml");
        } else {
            resourceEnv = resolver.getResource("classpath:application-local.yml");
        }
        Resource resource = resolver.getResource("classpath:pay.properties");
        Resource[] resources = {resource, resourceEnv};
        propertyHolder.setLocations(resources);
        return propertyHolder;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
