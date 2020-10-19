package com.onnoa.shop.demo.upload.controller;


import com.google.common.collect.Maps;
import com.onnoa.shop.demo.upload.dto.OcrCustomerOrderAttrDTO;
import com.onnoa.shop.demo.upload.service.CrmFileService;
import com.onnoa.shop.demo.upload.service.OrderCutImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LearnServiceTest {

    @Autowired
    CrmFileService crmFileService;
    @Autowired
    OrderCutImageService orderCutImageService;

    @Test
    public void getLearn() {
        System.out.println(UUID.randomUUID());
        String fileId = "db83965d2b91f2b5477bd8988897ff51_0";
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
}