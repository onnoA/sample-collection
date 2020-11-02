package com.onnoa.shop.demo.upload.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.onnoa.shop.demo.upload.dto.CodeBarResponse;
import com.onnoa.shop.demo.upload.dto.OcsRmInterfaceResponse;
import com.onnoa.shop.demo.upload.dto.PortInfoResponse;
import com.onnoa.shop.demo.upload.service.AbilityOpenApiService;
import com.onnoa.shop.demo.upload.service.OcsRmApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class OcsRmApiServiceImpl implements OcsRmApiService {

    @Autowired
    private AbilityOpenApiService openApiService;


    @Override
    public OcsRmInterfaceResponse qryBusPortInfo(String orderId) {
        String url = "http://134.176.102.33:8081/api/rest";
        String configStr = "{\n" +
                "\t\"method\": \"qry.resinfo.qryBusPortInfo\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"1\"\n" +
                "}";
        Map configParams = JSON.parseObject(configStr, Map.class);
        Map<String, Object> params = Maps.newHashMap();
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reqSystem", MapUtils.getString(configParams, "reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams, "reqPwd"));
        contentMap.put("DIS_SEQ", orderId);
        params.put("content", contentMap);
        params.put("status", MapUtils.getString(configParams, "status"));
        params.put("access_token", MapUtils.getString(configParams, "access_token"));
        params.put("method", MapUtils.getString(configParams, "method"));
        Map<String, Object> resultMap = openApiService.postAbility(params, url);
        log.info("数据返回:{}", resultMap);
        String resultStr = JSON.toJSONString(resultMap.get("result"));
        PortInfoResponse portInfoResponse = JSON.parseObject(resultStr, PortInfoResponse.class);
        //String returnCode = portInfoResponse.getBody().getOtherWSInterfaceResponse().getOut().getData().getReturn().getRETURN_CODE();
        OcsRmInterfaceResponse response = new OcsRmInterfaceResponse();
        //PortInfoResponse.BodyBean.OtherWSInterfaceResponseBean.OutBean.DataBean.ReturnBean returnBean = portInfoResponse.getBody().getOtherWSInterfaceResponse().getOut().getData().getReturn();
        PortInfoResponse.BodyBean.OtherWSInterfaceResponseBean.OutBean.DataBean.ReturnBean returnBean = Optional.ofNullable(portInfoResponse)
                .map(entity -> entity.getBody())
                .map(entity -> entity.getOtherWSInterfaceResponse())
                .map(entity -> entity.getOut())
                .map(entity -> entity.getData())
                .map(entity -> entity.getReturn())
                .orElse(new PortInfoResponse.BodyBean.OtherWSInterfaceResponseBean.OutBean.DataBean.ReturnBean());
        response.setData(returnBean);
        //String returnCode = returnBean.getRETURN_CODE();
        if (StringUtils.isNotBlank(returnBean.getRETURN_CODE()) && "0".equals(returnBean.getRETURN_CODE())) {
            response.setCode(0);
        } else {
            response.setCode(-1);
            response.setMessage(url + " 接口调用失败");
            log.error("接口响应异常,请求url:{}", url);
        }

        return response;
    }

    @Override
    public OcsRmInterfaceResponse qryCodeBarPortInfo(String obdScanCode) {
        String url = "http://134.176.102.33:8081/api/rest";
        String configStr = "{\n" +
                "\t\"method\": \"qry.resinfo.qryCodeBarPortInfo\",\n" +
                "\t\"access_token\": \"OTBkMjJiM2Y0NmVjNzdmOTc0NWFkZWMyZGU1MThhMmI=\",\n" +
                "\t\"reqSystem\": \"YWWB\",\n" +
                "\t\"reqPwd\": \"HNYWWB\",\n" +
                "\t\"status\": \"1\"\n" +
                "}";
        Map configParams = JSON.parseObject(configStr, Map.class);
        Map<String, Object> params = Maps.newHashMap();
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("reqSystem", MapUtils.getString(configParams, "reqSystem"));
        contentMap.put("reqPwd", MapUtils.getString(configParams, "reqPwd"));
        contentMap.put("CODE_BAR", obdScanCode);
        params.put("content", contentMap);
        params.put("status", MapUtils.getString(configParams, "status"));
        params.put("access_token", MapUtils.getString(configParams, "access_token"));
        params.put("method", MapUtils.getString(configParams, "method"));
        Map<String, Object> resultMap = openApiService.postAbility(params, url);
        log.info("数据返回:{}", resultMap);
        String resultStr = JSON.toJSONString(resultMap.get("result"));
        CodeBarResponse codeBarResponse = JSON.parseObject(resultStr, CodeBarResponse.class);
        CodeBarResponse.BodyBean.OtherWSInterfaceResponseBean.OutBean.DataBean.ReturnBean returnBean = Optional.ofNullable(codeBarResponse)
                .map(entity -> entity.getBody())
                .map(entity -> entity.getOtherWSInterfaceResponse())
                .map(entity -> entity.getOut())
                .map(entity -> entity.getData())
                .map(entity -> entity.getReturn())
                .orElse(new CodeBarResponse.BodyBean.OtherWSInterfaceResponseBean.OutBean.DataBean.ReturnBean());
        //CodeBarResponse.BodyBean.OtherWSInterfaceResponseBean.OutBean.DataBean.ReturnBean returnBean = codeBarResponse.getBody().getOtherWSInterfaceResponse().getOut().getData().getReturn();
        OcsRmInterfaceResponse response = new OcsRmInterfaceResponse();
        response.setData(returnBean);
        String returnCode = Optional.ofNullable(returnBean.getRETURN_CODE()).orElse("-1");
        if (returnCode.equals("0")) {
            response.setCode(0);
        } else {
            response.setCode(-1);
            response.setMessage(url + " 接口调用失败");
            log.error("接口响应异常,请求url:{}", url);
        }
        return response;
    }

}
