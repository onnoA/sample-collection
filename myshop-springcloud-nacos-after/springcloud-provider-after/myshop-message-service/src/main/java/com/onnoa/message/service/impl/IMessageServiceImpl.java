package com.onnoa.message.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.onnoa.message.service.IMessageService;
import com.onnoa.shop.common.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IMessageServiceImpl implements IMessageService {


    @Override
    public boolean sendMessage(String phoneNumber, String templateCode, Map<String, Object> verifyCode) {
        DefaultProfile profile = DefaultProfile.getProfile("ch-hangzhou", "LTAI4GB2hzCnTpqTD8PGrJNn", "ezNovhZJPKIv3TZu6tnYwQMy1yDS2E");
        DefaultAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        // 不要进行修改
        request.setSysDomain("dysmsapi.aliyuncs.com");
        // 不要进行修改
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        // 签名名称
        request.putQueryParameter("SignName", "onnoA");
        // 模板code
        request.putQueryParameter("TemplateCode", templateCode);
        // 验证码
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(verifyCode));
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            // 短信发送失败
            throw ServiceException.SMS_SEND_FAIL;
        }
    }
}
