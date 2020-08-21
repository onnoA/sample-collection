package com.onnoa.shop.common.properties.video;

import lombok.Data;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/6 12:02
 */
@Data
public class ScreenshotFormat {

    // 帧数
    private String frames;
    //最大分辨率宽度
    private String maxWidth;
    //默认截取GIF图片时 视频中的开始时间 (例如截取当前视频 第一秒开始的gif图 00:00:01)
    private String gifStartTime;
    //默认截GIF图的时长 (例如 3 的话 代表从开始时间截取3秒 00:00:1 到00:00:4
    private String burningTime;
    //默认截取jpg图片 视频中开始时间
    private String jpgStartTime;
    //截图质量比
    private String resultRate;


}
