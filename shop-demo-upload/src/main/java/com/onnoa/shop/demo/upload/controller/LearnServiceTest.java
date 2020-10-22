package com.onnoa.shop.demo.upload.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.onnoa.shop.common.utils.XMLUtils;
import com.onnoa.shop.demo.upload.dto.OcrCustomerOrderAttrDTO;
import com.onnoa.shop.demo.upload.dto.QryBusPortInfoDto;
import com.onnoa.shop.demo.upload.service.AbilityOpenApiService;
import com.onnoa.shop.demo.upload.service.CrmFileService;
import com.onnoa.shop.demo.upload.service.OrderCutImageService;
import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.JAXBException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnServiceTest {

    @Autowired
    CrmFileService crmFileService;
    @Autowired
    OrderCutImageService orderCutImageService;

    @Autowired
    private AbilityOpenApiService abilityOpenApiService;

    @Test
    public void getLearn() {
        System.out.println(UUID.randomUUID());
        String fileId = "a124285bbb9f86145f5b1468adc5acb2_0";
        byte[] download = crmFileService.download(fileId);
        System.out.println(Base64.getEncoder().encodeToString(download));
        if (download != null) {
            System.out.println("下载成功，长度： " + download.length);
            // 2.上传文件测试
            String file_id = crmFileService.upload(download, "pdf");
            if (file_id != null) {
                System.out.println("上传成功，返回文件ID:" + file_id);
            }
        }
    }


    @Test
    public void cutImage() {
        OcrCustomerOrderAttrDTO dto = new OcrCustomerOrderAttrDTO();
        dto.setCertiNumber("430611198608041512");
        dto.setLanId("730");
        dto.setLivingImage("4d04502ebad01a245979446ad9cfe087_0,b7516684abac624c6d9bc7d6d1f80989_0");
        dto.setCustOrderId("8730200923985677442");
        Map<String, Object> stringObjectMap = orderCutImageService.callbackImageClassify(dto);
        System.out.println(stringObjectMap);


    }


    @Test
    public void aiCheck() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("cust_order_id", "873120101255757238");
        params.put("auth_flag", "F");
        params.put("ai_result", "身份照片模糊");
        params.put("staff_no", "大数据稽核工号");
        Map<String, String> header = Maps.newHashMap();
        header.put("XAPPID", "f4751dc73f5f9914ca955b6649d8fde8");
        header.put("XAPPKEY", "131b9d55683a537d1a4c9e46ccfa673b");
        header.put("XCTGRequestID", UUID.randomUUID().toString());
        String url = "http://134.176.102.33:9080/api/openapi/INFSaveAuditResult/INFSaveAuditResult";
        Map<String, Object> stringObjectMap = orderCutImageService.callbackAiCheck(params, header, url);
        System.out.println(stringObjectMap);
    }

    @Test
    public void abilityPost() {
        String url = "http://134.176.102.33:8081/api/rest";
        Map<String, Object> requestMap = Maps.newHashMap();
        String configStr = "{\n" +
                "\t\"method\": \"qry.contract.QryContractFile\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"0\"\n" +
                "}";
        Map<String, Object> contentMap = new HashMap<>();
        Map configParams = JSON.parseObject(configStr, Map.class);
        contentMap.put("reqSystem", MapUtils.getString(configParams,"reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams,"reqPwd"));
        contentMap.put("contractCode", "HNCSA2006921CGN00");
        requestMap.put("content", contentMap);
        requestMap.put("status", MapUtils.getString(configParams, "status"));
        requestMap.put("access_token", MapUtils.getString(configParams, "access_token"));
        requestMap.put("method", MapUtils.getString(configParams, "method"));
        //Map<String, Object> resultMap = abilityOpenApiService.commonRequest(requestMap, url);
        Map<String, Object> resultMap = abilityOpenApiService.postAbility(requestMap, url);
        System.out.println("请求结束返回的结果====> :" + resultMap);
    }

    @Test
    public void abilityPost2() {
        String url = "http://134.176.102.33:8081/api/rest";
        Map<String, Object> requestMap = Maps.newHashMap();
        String configStr = "{\n" +
                "\t\"method\": \"qry.contract.QryContractFile\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"0\"\n" +
                "}";
        Map<String, Object> contentMap = new HashMap<>();
        Map configParams = JSON.parseObject(configStr, Map.class);
        contentMap.put("reqSystem", MapUtils.getString(configParams,"reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams,"reqPwd"));
        contentMap.put("contractCode", "HNCSA2006921CGN00");
        requestMap.put("content", contentMap);
        requestMap.put("status", MapUtils.getString(configParams, "status"));
        requestMap.put("access_token", MapUtils.getString(configParams, "access_token"));
        requestMap.put("method", MapUtils.getString(configParams, "method"));
        Map<String, Object> resultMap = abilityOpenApiService.postAbility(requestMap, url);
        System.out.println("请求结束返回的结果====> :" + resultMap);
    }

    @Test
    public void postAbility() throws JAXBException {
        String url = "http://134.176.102.33:8081/api/rest";
        Map<String, Object> requestMap = Maps.newHashMap();
        String configStr = "{\n" +
                "\t\"method\": \"qry.resinfo.qryBusPortInfo\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"0\"\n" +
                "}";
        Map configParams = JSON.parseObject(configStr, Map.class);
        QryBusPortInfoDto.OrderDto qryDto = new QryBusPortInfoDto.OrderDto();
        qryDto.setDisSeq("73011101972821306");
        QryBusPortInfoDto user = new QryBusPortInfoDto("qryBusPortInfo", qryDto,null);
        String inputXml = XMLUtils.bean2XmlString(user);
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reqSystem", MapUtils.getString(configParams,"reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams,"reqPwd"));
        contentMap.put("inputXml", inputXml);
        requestMap.put("content", contentMap);
        requestMap.put("status", MapUtils.getString(configParams, "status"));
        requestMap.put("access_token", MapUtils.getString(configParams, "access_token"));
        requestMap.put("method", MapUtils.getString(configParams, "method"));
        Map<String, Object> returnMap = abilityOpenApiService.postAbility(requestMap, url);
        System.out.println("请求返回的结果：{}" + returnMap);
    }
}