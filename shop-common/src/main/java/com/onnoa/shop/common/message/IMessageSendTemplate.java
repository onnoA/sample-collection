package com.onnoa.shop.common.message;

public interface IMessageSendTemplate {

    /**
     * 发送消息
     * 
     * @param topic 消息主题
     * @param data 消息数据
     */
    void sendMessage(String topic, String data);
}
