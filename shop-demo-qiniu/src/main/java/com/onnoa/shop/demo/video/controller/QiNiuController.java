package com.onnoa.shop.demo.video.controller;

import com.onnoa.shop.common.exception.ServiceExceptionUtil;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.video.constant.FileUploadErrorCode;
import com.onnoa.shop.demo.video.exception.FileUploadException;
import com.onnoa.shop.demo.video.qiniu.dto.ImageInfo;
import com.onnoa.shop.demo.video.service.QiniuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/28 09:31
 */
@RestController
@Slf4j
@Api(tags = "图片相关接口", description = "提供图片相关的 Rest API")
@RequestMapping(value = "/pic")
public class QiNiuController {

    @Autowired
    private QiniuService qiniuService;

    @PostMapping(value = "/add")
    @ApiOperation(value = "上传图片接口", notes = "上传图片")
    public ResultBean uploadPic(@RequestParam MultipartFile image) {
        try {
            if (image == null) {
                throw FileUploadException.FILE_NOT_ALLOW_NULL;
            }
            String key = qiniuService.uploadAndGetKey(image.getBytes());
            return ResultBean.success(key);
        } catch (IOException e) {
            return ResultBean.error(ServiceExceptionUtil.error(FileUploadErrorCode.FAILED_TO_UPLOAD_PIC));
        }
    }

    @GetMapping(value = "/getUrl")
    @ApiOperation(value = "根据key获取url", notes = "获取图片url")
    public ResultBean getPicUrlByKey(@RequestParam String imageKey) {
        return ResultBean.success(qiniuService.getImageInfo(imageKey));
    }


}
