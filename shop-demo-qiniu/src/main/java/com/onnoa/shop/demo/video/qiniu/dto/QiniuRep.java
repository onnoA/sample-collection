package com.onnoa.shop.demo.video.qiniu.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QiniuRep implements Serializable {

    private long fsize;
    private String key;
    private String hash;
    private int width;
    private int height;

}
