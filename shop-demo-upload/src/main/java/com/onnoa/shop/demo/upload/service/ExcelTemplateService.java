package com.onnoa.shop.demo.upload.service;

import com.onnoa.shop.common.result.ResultBean;

import java.util.Map;

public interface ExcelTemplateService {

    /**
     * excel 模板下载
     * @param params
     * @return
     */
    public ResultBean downTemplate(Map<String,Object> params);
}
