package com.onnoa.shop.demo.upload.controller;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.upload.dto.OcsRmInterfaceResponse;
import com.onnoa.shop.demo.upload.service.OcsRmApiService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 端口占用控制层测试类
 */
@RestController
@RequestMapping(value = "/ocs")
public class OcsRmController {

    @Autowired
    private OcsRmApiService ocsRmApiService;

    @ResponseBody
    @PostMapping("/qryBusPortInfo")
    public ResultBean qryBusPortInfo(@RequestBody Map<String, Object> params) {
        String orderId = MapUtils.getString(params, "orderId");
        OcsRmInterfaceResponse response = ocsRmApiService.qryBusPortInfo(orderId);
        return ResultBean.success(response);
    }

    @ResponseBody
    @PostMapping("/qryCodeBarPortInfo")
    public ResultBean addContractTemplate(@RequestBody Map<String, Object> params) {
        String obdScanCode = MapUtils.getString(params, "obdScanCode");
        OcsRmInterfaceResponse response = ocsRmApiService.qryCodeBarPortInfo(obdScanCode);
        return ResultBean.success(response);
    }
}
