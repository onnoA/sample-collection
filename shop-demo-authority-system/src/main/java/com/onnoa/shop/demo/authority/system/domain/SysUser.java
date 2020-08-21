package com.onnoa.shop.demo.authority.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户名称
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码MD5
     */
    @TableField(value = "password")
    private String password;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 手机号
     */
    @TableField(value = "mobile_phone")
    private String mobilePhone;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录IP
     */
    @TableField(value = "last_login_ip")
    private String lastLoginIp;

    /**
     * 登录次数
     */
    @TableField(value = "login_count")
    private Integer loginCount;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 用户状态1-正常，2-禁用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 是否可以删除用户 0-正常，1删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    private String creator;

    private static final long serialVersionUID = 1L;
}
