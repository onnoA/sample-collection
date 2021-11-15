package com.onnoa.shop.demo.authority.system.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyCodeVo implements Serializable {

    // 验证码base64
    private String codeBase64;

    // 验证码uuid
    private String codeUUID;
}
