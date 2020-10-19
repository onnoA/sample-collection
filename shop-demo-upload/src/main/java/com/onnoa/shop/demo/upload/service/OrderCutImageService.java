package com.onnoa.shop.demo.upload.service;

import com.onnoa.shop.demo.upload.dto.OcrCustomerOrderAttrDTO;

import java.util.Map;

public interface OrderCutImageService {

    Map<String, Object> callbackImageClassify(OcrCustomerOrderAttrDTO customerOrder);

    Map<String, Object> callbackAiCheck(Map<String, Object> params, Map<String, String> header, String url);
}
