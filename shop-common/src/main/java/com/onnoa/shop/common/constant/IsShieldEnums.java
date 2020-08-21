package com.onnoa.shop.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 是否屏蔽枚举类
 * @Author: onnoA
 * @Date: 2020/7/5 10:56
 */
@Getter
@AllArgsConstructor
public enum  IsShieldEnums {

    NORMAL(1, "正常"),
    SHIELDED(2, "屏蔽"),
    ;


    private int code;
    private String msg;

}
