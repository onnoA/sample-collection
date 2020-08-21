package com.onnoa.distributed.primary.key.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/8/8 14:48
 */
@AllArgsConstructor
@Getter
public enum KeyTypeEnum {
    REDIS(1, "REDIS"), SNOWFLAKE(2, "SNOWFLAKE"), UID_GENERATOR(3, "UID_GENERATOR"), LEAF(4, "LEAF"), OTHER(5, "OTHER");

    private int key;

    private String value;

    public static KeyTypeEnum getByKey(int key) {
        for (KeyTypeEnum typeEnum : values()) {
            if (typeEnum.key == (key)) {
                return typeEnum;
            }
        }
        return null;
    }
}
