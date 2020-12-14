package com.onnoa.shop.demo.upload.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class SmsAlertsDto implements Serializable {

    // 本地网
    private String lanId;

    // 产品实例
    private String targetObjId;

    // 短信内容
    private String others;

    // 号码
    private String contactNbr;

    // 号码
    private String accsNbr;


}
