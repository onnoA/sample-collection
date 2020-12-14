package com.onnoa.shop.demo.upload.service.impl;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.common.utils.HttpUtil;
import com.onnoa.shop.demo.upload.service.ExcelTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ExcelTemplateServiceImpl implements ExcelTemplateService {


    @Override
    public ResultBean downTemplate(Map<String, Object> params) {
        log.info("=========下载excel模板=========");
        HttpServletResponse response = HttpUtil.getResponse();
        response.setContentType("application/force-download");
        try {
            String fileName = URLEncoder.encode(String.format("%s-(%s).xlsx", "报表", UUID.randomUUID().toString()),
                    StandardCharsets.UTF_8.toString());
//            ClassPathResource classPathResource = new ClassPathResource("exceltemplate/dataexcel.xlsx");
//            InputStream inputStream = classPathResource.getInputStream();
            String path = this.getClass().getResource("exceltemplate/dataexcel.xlsx").getFile();
            File refereeFile = new File(path);
            byte[] bFile = FileUtils.readFileToByteArray(refereeFile);
            //InputStream inputStream = this.getClass().getResourceAsStream("/exceltemplate/dataexcel.xlsx");
            //InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("/exceltemplate/dataexcel.xlsx");
            //byte[] bFile = StreamUtils.copyToByteArray(inputStream);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Type", "application/ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("dataexcel.xlsx", "UTF-8"))));
            response.getOutputStream().write(bFile);
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("=========下载模板出错！！！！=========  {}", e);
            return ResultBean.error(e.getMessage());
        }
        return ResultBean.success();
    }
}
