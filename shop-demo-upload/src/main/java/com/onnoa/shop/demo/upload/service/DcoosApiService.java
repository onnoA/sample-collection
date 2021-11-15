package com.onnoa.shop.demo.upload.service;

import java.util.Map;

public interface DcoosApiService {

    Map<String, Object> post(Map<String, Object> params, Map<String, String> configParams);

    Map<String, Object> post(Map<String, Object> params, Map<String, String> header, String url);


}
