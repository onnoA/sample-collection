package com.onnoa.shop.demo.upload.controller;

import com.google.common.collect.Maps;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.upload.service.ExcelTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/excel")
public class DownloadController {

    @Autowired
    private ExcelTemplateService excelTemplateService;

    @PostMapping(value = "/down")
    public void down() {
        Map<String, Object> params = Maps.newHashMap();
        excelTemplateService.downTemplate(params);


    }
}
