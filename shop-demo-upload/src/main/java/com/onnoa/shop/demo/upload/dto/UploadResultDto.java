package com.onnoa.shop.demo.upload.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UploadResultDto implements Serializable {

    private String fileName;

    private String fileDownloadUri;

}
