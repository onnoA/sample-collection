package com.onnoa.shop.demo.video.utils;

import lombok.Data;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/8 00:26
 */
@Data
public class VideoResolveResult {

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 总码率
     */
    private Long tlBitrate;

    /**
     * 视频码率
     */
    private Long vdBitrate;

    /**
     * 音频码率
     */
    private Long adBitrate;

    /**
     * 宽
     */
    private Integer videoWidth;

    /**
     * 高
     */
    private Integer videoHeight;


    /**
     * 横屏旋转角度
     */
    private String rotate;

    /**
     *  总时长
     *
     */
    private Double duration;

}
