package com.onnoa.shop.common.message;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.common.collect.Maps;

import java.util.Map;

public class AliMessage {
    public static void main(String[] args) {

        DefaultProfile profile = DefaultProfile.getProfile("ch-hangzhou", "LTAI4GB2hzCnTpqTD8PGrJNn", "ezNovhZJPKIv3TZu6tnYwQMy1yDS2E");
        DefaultAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        request.putQueryParameter("PhoneNumbers", "13422129567");
        request.putQueryParameter("SignName", "onnoA");
        request.putQueryParameter("TemplateCode", "SMS_203716093");
        Map<String, Object> map = Maps.newHashMap();
        map.put("code", 6789);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
