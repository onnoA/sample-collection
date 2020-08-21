package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/23 12:19
 */
@Data
public class BaseSysBackEndInterResourceDto implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 访问路径
     */
    private String interfaceUrl;

    /**
     * 菜单功能名称
     */
    private String interfaceName;

    /**
     * 描述
     */
    private String interfaceDescr;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
