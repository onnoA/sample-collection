package com.onnoa.shop.demo.authority.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 前端可视资源的种类
 * @Author: onnoA
 * @Date: 2020/7/23 11:38
 */
@AllArgsConstructor
@Getter
public enum FrontViewTypeEnum {
    FOLDER("1","菜单文件夹"),
    FILE("2","菜单文件"),
    BUTTON("3","按钮功能");

    private String type;
    private String name;
}
