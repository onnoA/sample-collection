package com.onnoa.shop.demo.authority.system.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyCodeVo implements Serializable {

    private String codeBase64;

    private String codeUUID;
}
