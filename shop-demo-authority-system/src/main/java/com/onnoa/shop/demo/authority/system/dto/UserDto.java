package com.onnoa.shop.demo.authority.system.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDto implements Serializable {

    // 用户id
    private String id;

    // 登录名
    private String username;

    // 真实名字
    private String realName;

    // 电话号码
    private String mobilePhone;

    // 角色权限列表
    private String roleListStr;

    // 当前用户所拥有的角色ID列表
    // private List<String> roleIdList;

    // 登录次数
    private Integer loginCount;

    // 最后登录ip
    private String lastLoginIp;

    // 最后登录时间 返回数据格式: "2019-04-28 08:24:14"
    @JsonFormat(timezone = "GMT+8", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

}
