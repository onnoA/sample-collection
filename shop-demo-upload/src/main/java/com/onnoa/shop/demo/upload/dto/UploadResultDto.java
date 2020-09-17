package com.onnoa.shop.demo.upload.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UploadResultDto implements Serializable {

    private String fileName;

    private String fileDownloadUri;
}
