package com.onnoa.shop.demo.video.constant;

import com.onnoa.shop.common.exception.ErrorCode;

public enum VideoErrorCodeEnum implements ErrorCode {


    VIDEO_FAIL_TO_UPLOAD(300000001, "视频上传失败,请稍后再试。。"),

    SYSTEM_GATEWAY_ERROR(1000000002, "系统出错 >> "),

    COMMON_PARAMS_IS_ILLICIT(1000000003, "参数{}非法"),


    ;


    private int code;
    private String message;

    VideoErrorCodeEnum(int code, String message) {
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
