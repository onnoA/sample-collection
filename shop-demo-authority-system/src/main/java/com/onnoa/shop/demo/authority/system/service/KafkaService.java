package com.onnoa.shop.demo.authority.system.service;

import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.onnoa.shop.common.constant.GlobalConstant;
import com.onnoa.shop.common.utils.BeanUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaService {

    @KafkaListener(topics = {
        GlobalConstant.SLOW_SQL_LOG
    })
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Map<String, Object> map = BeanUtils.beanToMap(JSONObject.parse(kafkaMessage.get().toString()));
            log.info("消费者接收到的kafka消息:{}", JSONObject.toJSON(map));
            // 执行其他业务

        }
    }
}
