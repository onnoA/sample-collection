package com.onnoa.shop.demo.authority.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
@TableName(value = "sys_front_view_resource")
public class SysFrontViewResource implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 父级id
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 菜单路径
     */
    @TableField(value = "path")
    private String path;

    /**
     * 菜单名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 菜单描述
     */
    @TableField(value = "descr")
    private String descr;

    /**
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 类型：1菜单文件夹2菜单文件3按钮功能
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 层级
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 排序
     */
    @TableField(value = "sort")
    private Integer sort;

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

    @TableField(exist = false)
    private List<SysFrontViewResource> children = new ArrayList<>();

    private static final long serialVersionUID = 1L;
}
