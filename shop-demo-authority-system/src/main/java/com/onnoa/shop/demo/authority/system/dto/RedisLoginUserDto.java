package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/7 00:00
 */
@Data
public class RedisLoginUserDto {
    /**
     * 用户id
     */
    private String id;

    /**
     * accessToken令牌
     */
    private String accessToken;

    /**
     * 登录或刷新应用的时间
     */
    private long refTime;
    /**
     * 用户名
     */
    private String username;
    /**
     * 登录密码
     */
    private String password;

}
