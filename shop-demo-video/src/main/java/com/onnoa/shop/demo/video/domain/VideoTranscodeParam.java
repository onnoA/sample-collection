package com.onnoa.shop.demo.video.domain;

import lombok.Data;


/**
 * @author onnoA
 * @Description 视频转码参数
 * @date 2021/7/6 16:37
 */
@Data
public class VideoTranscodeParam {
    private String sourceVideoPath;
    private String outputVideoPath;
    private String uuid;
    private String ffmpegPath;
}
