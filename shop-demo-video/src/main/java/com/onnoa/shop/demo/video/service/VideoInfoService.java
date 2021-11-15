package com.onnoa.shop.demo.video.service;

import com.baomidou.mybatisplus.service.IService;
import com.onnoa.shop.demo.video.domain.VideoInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 视频相关Service
 * @Author: onnoA
 * @Date: 2020/6/5 23:33
 */
public interface VideoInfoService extends IService<VideoInfo> {

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

    /**
     * @Description 视频处理
     * @author onnoA
     * @date 2021/7/6 16:24
     * @param videoInfo 视频信息对象
     */
    void videoProcess(VideoInfo videoInfo);

    void test(VideoInfo videoInfo);

}
