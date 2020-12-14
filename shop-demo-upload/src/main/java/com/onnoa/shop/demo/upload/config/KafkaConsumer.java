package com.onnoa.shop.demo.upload.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {


    // 消费监听
    @KafkaListener(topics = {"topic3"})
    public void listen2(@Payload List<String> data,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) List<Long> tss) {
        System.out.println("收到" + data.size() + "条消息：");
        System.out.println(data);
        System.out.println(topics);
        System.out.println(partitions);
        System.out.println(keys);
        System.out.println(tss);
    }
}
