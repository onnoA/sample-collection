package com.onnoa.shop.demo.video.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 视频相关Service
 * @Author: onnoA
 * @Date: 2020/6/5 23:33
 */
public interface VideoInfoService {

    /**
     * 功能描述: 视频上传
     *
     * @param videoId 视频id
     * @param file    视频文件
     * @param request 请求
     * @return true 成功
     * @date 2020/6/6 9:22
     */
    boolean uploadVideo(String videoId, MultipartFile file, HttpServletRequest request);
}
