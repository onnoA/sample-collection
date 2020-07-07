package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class AuthDto implements Serializable {

    // 用户名
    @NotBlank(message = "用户名不能为空。")
    private String username;

    // 请求前端path
    @NotBlank(message = "前端path不能为空。")
    private String frontPath;

    // 请求后端接口
    @NotBlank(message = "请求后端接口url不能为空。")
    private String interfaceUrl;

}
