package com.onnoa.shop.demo.upload.queue;

import com.onnoa.shop.common.message.IMessageSendTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@Primary
public class KafkaSender implements IMessageSendTemplate {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 发送消息
     *
     * @param topic
     * @param data
     */
    @Override
    public void sendMessage(String topic, String data) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
                //throw new RuntimeException("kafka message send fail");
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("kafka sendMessage success topic = {}, data = {}", topic, data);
            }
        });
    }
}
