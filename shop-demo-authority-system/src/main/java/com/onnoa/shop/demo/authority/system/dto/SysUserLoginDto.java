package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SysUserLoginDto implements Serializable {


    /**
     * 用户名称
     */
    @NotBlank(message = "用户名不能为空。")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空。")
    private String password;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空。")
    private String verifyCode;

    /**
     * 验证码uuid
     */
    @NotBlank(message = "uuid不能为空。")
    private String codeUUID;


}
