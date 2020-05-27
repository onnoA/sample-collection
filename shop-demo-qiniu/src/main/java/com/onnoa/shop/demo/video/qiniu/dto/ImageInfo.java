package com.onnoa.shop.demo.video.qiniu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageInfo implements Serializable {

    private long size;
    private String format;
    private String url;
    private String colorModel;
    private int width;
    private int height;

    public ImageInfo() {
    }

    public ImageInfo(String url, int width, int height) {
        super();
        this.url = url;
        this.width = width;
        this.height = height;
    }

}
