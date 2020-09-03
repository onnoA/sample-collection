package com.onnoa.shop.demo.authority.system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AddOrUpdateResourceInfoDto implements Serializable {

    private String viewId;

    @NotBlank(message = "父级菜单 id 不能为空。")
    private String parentId;

    /**
     * 菜单名字
     */
    @NotBlank(message = "名称不能为空。")
    @Length(max = 20, message = "名称过长")
    @Pattern(regexp = "^[A-z0-9\\u4e00-\\u9fa5]*$",message = "名称不能包含非法字符。")
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 层级
     */
    private int level;

    /**
     * 排序
     */
    private int sort;

    /**
     * 父级菜单名称
     */
    private String parentName;

    /**
     * 前端菜单路径
     */
    private String path;

    /**
     * 后端接口访问路径
     */
    private String interfaceUrl;

    /**
     * 类型：1菜单文件夹2菜单文件3按钮功能
     */
    @NotBlank
    @Pattern(regexp = "1|2|3", message = "类型必须为1、2、3。")
    // @Pattern(regexp = "^[1-3]{1}$", message = "类型必须为1、2、3。")
    private String type;


}
