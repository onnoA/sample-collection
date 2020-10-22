package com.onnoa.shop.demo.video.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.shaded.com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: ffmpeg 可以解析的视频格式： asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv
 * @Author: onnoA
 * @Date: 2020/6/6 09:36
 */
@Getter
@AllArgsConstructor
@Slf4j
public enum VideoFileFormatEnum {

    MP4_VIDEO_FORMAT(1, ".mp4"),
    AVI_VIDEO_FORMAT(2, ".avi"),
    MPG_VIDEO_FORMAT(3, ".mpg"),
    WMV_VIDEO_FORMAT(4, ".wmv"),
    MOV_VIDEO_FORMAT(5, ".mov"),
    FLV_VIDEO_FORMAT(6, ".flv"),


    ;

    public static void main(String[] args) {
        VideoFileFormatEnum videoFileFormatEnum = VideoFileFormatEnum.valueOf("MP4_VIDEO_FORMAT");
        System.out.println(videoFileFormatEnum.getCode());
        System.out.println(videoFileFormatEnum.getFormat());
    }

    private int code;
    private String format;

    public static List<Map<String, Object>> getVideoFormat() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (VideoFileFormatEnum videoFileFormat : values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", videoFileFormat.code);
            map.put("videoFileFormat", videoFileFormat.format);
            list.add(map);
        }
        return list;
    }

    public static List<String> getAllFormat() {
        List<String> list = Lists.newArrayList();
        for (VideoFileFormatEnum videoFileFormatEnum : VideoFileFormatEnum.values()) {
            list.add(videoFileFormatEnum.getFormat());
        }
        return list;
    }

    public static final VideoFileFormatEnum getVideoFormatByCode(int code) {
        for (VideoFileFormatEnum videoFileFormat : values()) {
            if (code == videoFileFormat.code) {
                return videoFileFormat;
            }
        }
        return null;
    }


}
