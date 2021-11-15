package com.onnoa.spring.configuration.metadata.config.controller;

import com.google.common.collect.Maps;
import com.onnoa.spring.configuration.metadata.config.config.MyMapConfig;
import com.onnoa.spring.configuration.metadata.config.dto.OneUser;
import com.onnoa.spring.configuration.metadata.config.dto.TwoUser;
import com.onnoa.spring.configuration.metadata.config.strategy.BusinessHandlerChoose;
import com.onnoa.spring.configuration.metadata.config.strategy.BusinessStrategyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(value = "/user")
@RestController
public class UserAnnotationController {

    @Autowired
    private BusinessHandlerChoose businessHandlerChoose;

    @GetMapping(value = "/strategyTest")
    public Map strategyTest(){
        Map<String, Object> map = Maps.newHashMap();
        OneUser oneUser = (OneUser) businessHandlerChoose.businessHandlerChooser(BusinessStrategyConstant.DemoOne.HAHA, "1").businessProcess(1l);
        map.put("result", oneUser);

        TwoUser twoUser = (TwoUser) businessHandlerChoose.businessHandlerChooser(BusinessStrategyConstant.DemoOne.HEHE, "2").businessProcess("zh");
        System.out.println("返回结果:"+ twoUser);
        return map;
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyMapConfig.class);
        Map map = (Map) applicationContext.getBean("map");
        System.out.println(map);

    }

}
