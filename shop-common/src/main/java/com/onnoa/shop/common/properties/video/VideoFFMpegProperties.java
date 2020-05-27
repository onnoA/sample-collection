package com.onnoa.shop.common.properties.video;

import lombok.Data;

/**
 * @Description: 自定义 FFMpeg 配置
 * @Author: onnoA
 * @Date: 2020/6/6 11:59
 */
@Data
public class VideoFFMpegProperties {

    // 视频处理工具
    private String ffmpegPath;

    // 读取视频工具
    private String ffprobePath;

    // 缓存视频封面截图处理的中间临时图片文件（需要定时清理）
    private String screenShotPath;

    // 缓存视频转码输出的临时视频文件（需要定时清理）
    private String transcodeOutputPath;

    // 最大可上传为200M
    private long fileMaxSize;

    // 调节编码速度 从左到右 从快到慢 ultrafast、superfast、veryfast、faster、fast、medium、slow、slower编码加快，意味着信息丢失越严重，输出图像质量越差。
    private String speed;

    // 视频时长要求,限制生成gif小于该值则抛异常
    private int duration;

    // 配置截图格式
    private ScreenshotFormat screenshotFormat = new ScreenshotFormat();


}
