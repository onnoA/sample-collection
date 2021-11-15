package com.onnoa.shop.demo.video.dto;

import lombok.Data;


@Data
public class QiniuTokenDto {

    private String accessKey;

    private String upToken;

    private String commandToken;
}
