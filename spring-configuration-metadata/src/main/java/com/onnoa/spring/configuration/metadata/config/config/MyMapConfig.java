package com.onnoa.spring.configuration.metadata.config.config;

import cn.hutool.core.collection.CollUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: MyMapConfig
 * @description:
 * @author: onnoA
 * @date: 2021/9/16
 **/
@Configuration
public class MyMapConfig {

    @Bean(value = "map")
    public Map map(){
        Map map = new HashMap();
        map.put("test", "test");
        System.out.println(map);
        return map;
    }
}
