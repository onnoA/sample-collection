package com.onnoa.shop.demo.comment.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否删除枚举常量
 */
@Getter
@AllArgsConstructor
public enum IsDeletedEnums {

    NORMAL(0, "正常"),
    DELETED(1, "删除"),
    ;


    private int code;
    private String msg;
}
