package com.onnoa.shop.demo.upload.controller;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.upload.exception.FileException;
import com.onnoa.shop.demo.upload.service.CrmFileService;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@Controller
@RequestMapping(value = "/image")
@RestController
public class ImageController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ImageController.class);


    @Autowired
    CrmFileService crmFileService;

    /**
     * 从crm获取文件流
     *
     * @param fileId
     * @param response
     * @throws Exception
     */
    @GetMapping("/getCrmFile")
    public void getCrmFile(@Param("fileId") String fileId, HttpServletResponse response) {
        if (StringUtils.isEmpty(fileId)) {
            return;
        }
        OutputStream outputStream = null;
        try {
            byte[] buf = crmFileService.download(fileId);
            outputStream = response.getOutputStream();
            outputStream.write(buf, 0, buf.length);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            logger.error("输出文件流出错", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return;
    }

    /**
     * 上传文件到crm
     *
     * @param file
     * @throws Exception
     */
    @RequestMapping(path = "/upLoadCrmFile", method = RequestMethod.POST)
    public ResultBean upLoadCrmFile(@RequestParam("fileType") String fileType, @RequestParam(value = "file", required = false) MultipartFile file) {
        byte[] bytes = null;
        String fileId = "";
        try {
            bytes = file.getBytes();
            fileId = crmFileService.upload(bytes, fileType);

        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            throw FileException.FILE_UPLOAD_FAILED.format(file.getOriginalFilename());
        }
        return ResultBean.success(fileId);
    }


}