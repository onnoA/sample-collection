package com.onnoa.shop.demo.video.domain;

import lombok.Data;


/**
 * @author onnoA
 * @Description
 * @date 2021/7/6 16:29
 */
@Data
public class VideoResult {
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
     * 总时长
     */
    private Double duration;

}
