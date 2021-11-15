package com.onnoa.shop.demo.authority.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/7/18 17:22
 */
@Data
public class FrontViewResourceVo implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单描述
     */
    private String descr;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型：1菜单文件夹2菜单文件3按钮功能
     */
    private String type;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private List<FrontViewResourceVo> children = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}
