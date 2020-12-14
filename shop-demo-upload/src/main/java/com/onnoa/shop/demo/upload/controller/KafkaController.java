package com.onnoa.shop.demo.upload.controller;

import com.google.common.collect.Maps;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.upload.service.ExcelTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {


    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @PostMapping(value = "/test")
    public ResultBean test() {
        for (int i = 0; i < 1000; i++) {
            kafkaTemplate.send("topic3", "message-" + i);
        }
        return ResultBean.success();

    }


}
