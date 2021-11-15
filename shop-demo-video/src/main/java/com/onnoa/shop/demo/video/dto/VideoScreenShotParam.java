package com.onnoa.shop.demo.video.dto;

import lombok.Data;

/**
 * @program: crmcloud-master
 * @description: 视频转码参数
 * @author: will
 * @create: 2020-04-07 11:27
 **/
@Data
public class VideoScreenShotParam {
    /**
     * 原视频地址
     */
    private String sourceVideoPath;
    /**
     * ffmpeg地址
     */
    private String ffmpegPath;
    /**
     * 截图生成后的路径
     */
    private String screenShotPath;
    /**
     * 截图的文件名称
     */
    private String screenShotName;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 长度
     */
    private String length;
    /**
     *视频宽度
     */
    private Integer videoWidth;
}
