package com.onnoa.message.service;

import java.util.Map;

public interface IMessageService {

    public boolean sendMessage(String phoneNumber, String templateCode, Map<String, Object> verifyCode);
}
