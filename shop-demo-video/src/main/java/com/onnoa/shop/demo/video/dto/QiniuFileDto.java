package com.onnoa.shop.demo.video.dto;

import lombok.Data;

/**
 * @author onnoA
 * @Description 七牛文件Dto
 * @date 2021/7/6 23:20
 */
@Data
public class QiniuFileDto {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private Long fsize;
    /**
     * 文件key
     */
    private String key;
    /**
     * 图片宽度
     */
    private Integer width;
    /**
     * 图片高度
     */
    private Integer height;
    /**
     * 文件类型
     */
    private FileType fileType;

    /**
     * 文件hash
     */
    private String hash;

    /**
     * 文件类型枚举
     */
    public enum FileType {
        Img, Text, Media
    }
}
