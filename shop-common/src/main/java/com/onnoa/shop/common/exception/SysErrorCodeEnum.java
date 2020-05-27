package com.onnoa.shop.common.exception;

/**
 * @Description: 系统级别错误
 * @Author: onnoA
 * @Date: 2020/5/21 20:23
 */
public enum SysErrorCodeEnum {

    SYSTEM_INTERNAL_ERROR(1000000001,"系统不舒服，请稍后再试。。。"),

    SYSTEM_GATEWAY_ERROR(1000000002,"系统出错 >> "),

    COMMON_PARAMS_IS_ILLICIT(1000000003,"参数{}非法"),



            ;

    private final int code;
    private final String message;

    SysErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ;


}


