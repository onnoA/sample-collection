package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserLoginDto implements Serializable {


    /**
     * 用户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 验证码uuid
     */
    private String uuid;


}
