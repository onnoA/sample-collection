package com.onnoa.shop.demo.upload.service;

import java.util.Map;

public interface AbilityOpenApiService {
    Map<String,Object> commonRequest(Map<String, Object> params, String url);

    Map<String,Object> postAbility(Map<String, Object> params, String url);
}
