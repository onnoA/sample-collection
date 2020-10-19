package com.onnoa.shop.demo.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Maps;
import com.onnoa.shop.demo.upload.dto.OcrCustomerOrderAttrDTO;
import com.onnoa.shop.demo.upload.service.DcoosApiService;
import com.onnoa.shop.demo.upload.service.OrderCutImageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.alibaba.druid.stat.DruidStatService.RESULT_CODE_SUCCESS;

@Service
@Slf4j
public class OrderCutImageServiceImpl implements OrderCutImageService {

    /**
     * 程序运行标识， 0是正常，-1是异常
     */
    public static final String RESULT_CODE = "RESULT_CODE";
    /**
     * 程序出错标识
     **/
    public static final String ERROR_MSG = "ERROR_MSG";
    /**
     * 程序运行标识
     **/
    public static final String RESULT_MSG = "RESULT_MSG";

    /**
     * 后台返回前台操作失败
     */
    public static final String RESULT_CODE_FAIL = "-1";

    @Autowired
    DcoosApiService dcoosApiService;

    @Override
    public Map<String, Object> callbackImageClassify(OcrCustomerOrderAttrDTO customerOrder) {
        Map<String, Object> returnObj = new HashMap<>();
        // 调接口
        String callbackMethod = "INFSaveClassifiedImages/INFSaveClassifiedImages";
        String configParamsStr = "{\"basicUrl\":\"http://134.176.102.33:9080/api/openapi\", \"XAPPID\":\"f4751dc73f5f9914ca955b6649d8fde8\", \"XAPPKEY\":\"131b9d55683a537d1a4c9e46ccfa673b\", \"clientId\":\"AI_APR\"}";
        Map<String, Object> params = new HashMap<>();
        Map customerOrderMap = new HashMap();
        String livingImage = customerOrder.getLivingImage();
        String livingImage1 = "";
        String livingImage2 = "";
        if (!StringUtil.isEmpty(livingImage)) {
            String[] livingImageArr = livingImage.split(",");
            if (livingImageArr != null) {
                if (livingImageArr.length == 1) {
                    livingImage1 = livingImageArr[0];
                } else if (livingImageArr.length > 1) {
                    livingImage1 = livingImageArr[0];
                    livingImage2 = livingImageArr[1];
                }
            }
        }
        customerOrderMap.put("cust_order_id", customerOrder.getCustOrderId());
        customerOrderMap.put("lan_id", customerOrder.getLanId());
        customerOrderMap.put("living_img_file1", livingImage1);
        customerOrderMap.put("living_img_file2", livingImage2);
        customerOrderMap.put("cert_photo_file1", customerOrder.getCertImageFront());
        customerOrderMap.put("cert_photo_file2", customerOrder.getCertImageBack());
        customerOrderMap.put("cert_img_file", customerOrder.getCertImageFile());
        customerOrderMap.put("certi_number", customerOrder.getCertiNumber());
        params.put("params", customerOrderMap);
        params.put("method", callbackMethod);
        String resultMsg = "";
        try {
            Map configParams = JSON.parseObject(configParamsStr, Map.class);
            Map resultMap = dcoosApiService.post(params, configParams);
//                String result = HttpClient.post(callbackUrl, JSON.toJSONString(contractInstVO));
            if (MapUtils.getBoolean(resultMap, "flag")) {
                JSONObject res = (JSONObject) resultMap.get("result");
                if ("0000".equals(res.getString("service_code"))) {
                    returnObj.put(RESULT_CODE, RESULT_CODE_SUCCESS);
                } else {
                    returnObj.put(ERROR_MSG, JSON.toJSONString(resultMap));
                    returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);
                }
            } else {
                returnObj.put(ERROR_MSG, JSON.toJSONString(resultMap));
                returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);
            }
        } catch (Exception e) {
            returnObj.put(ERROR_MSG, "合同解析结果回传 - 调用接口出错" + e);
            returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);
        }
        return returnObj;
    }

    @Override
    public Map<String, Object> callbackAiCheck(Map<String, Object> params, Map<String, String> header, String url) {
        Map<String, Object> returnObj = Maps.newHashMap();
        try {
            params.put("params", params);
            //params.put("method", callbackMethod);
            Map resultMap = dcoosApiService.post(params, header, url);
            if (MapUtils.getBoolean(resultMap, "flag")) {
                JSONObject res = (JSONObject) resultMap.get("result");
                if ("0000".equals(res.getString("code"))) {
                    returnObj.put(RESULT_CODE, RESULT_CODE_SUCCESS);
                } else {
                    returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);
                }
                returnObj.put(RESULT_MSG, JSON.toJSONString(resultMap));
            } else {
                returnObj.put(RESULT_MSG, JSON.toJSONString(resultMap));
                returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            returnObj.put(RESULT_MSG, "合同解析结果回传 - 调用接口出错" + e.getMessage());
            returnObj.put(RESULT_CODE, RESULT_CODE_FAIL);

        }
        return returnObj;
    }
}
