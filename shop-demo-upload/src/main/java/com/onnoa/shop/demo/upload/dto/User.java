package com.onnoa.shop.demo.upload.dto;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最近操作人
     */
    private String lastOperator;

    /**
     * 最后操作人ID
     */
    private Long lastOperatorId;

    /**
     * 更新时间
     */
    private Date updateTime;

    //地址
    private String address;

    //描述/详情/备注
    private String description;

    //邮件
    private String email;

    //手机
    private String mobile;

    //昵称
    private String nickName;

    //密码
    private String password;

    //0女 1男 2保密
    private Integer sex;

    //用户头像
    private String avatar;

    //用户类型 0普通用户 1管理员
    private Integer type;

    //状态 默认0正常 -1拉黑
    private Integer status;

    //登录名
    private String loginName;

    //用户名
    private String username;

    //所属部门id
    private Long departmentId;

    //最后登录时间
    private Date lastLoginTime;

    //最后登录IP地址
    private String lastLoginIp;

    private String lastLoginLocation;

    //连续输错密码次数
    private Integer pwdErrorCount;

    //最后输错密码时间
    private Date pwdErrorTime;

    //用户来源
    private Integer userSource;

    //所属部门名称
    private String departmentTitle;
}
