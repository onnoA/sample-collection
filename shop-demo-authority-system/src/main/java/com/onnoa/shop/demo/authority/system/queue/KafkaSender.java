package com.onnoa.shop.demo.authority.system.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.onnoa.shop.common.message.IMessageSendTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Primary
public class KafkaSender implements IMessageSendTemplate {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    /**
     * 发送消息
     * 
     * @param topic
     * @param data
     */
    @Override
    public void sendMessage(String topic, String data) {
        for (int i = 0; i < 10; i++) {
            ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, data);
            future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
                @Override
                public void onFailure(Throwable ex) {
                    log.error("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
                }

                @Override
                public void onSuccess(SendResult<Integer, String> result) {
                    log.info("kafka sendMessage success topic = {}, data = {}", topic, data);
                }
            });
        }
    }
}
