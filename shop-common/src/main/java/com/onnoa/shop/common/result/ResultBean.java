package com.onnoa.shop.common.result;

import com.onnoa.shop.common.exception.ErrorCode;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * @Description: 包装通用的返回结果
 * @Author: onnoA
 * @Date: 2020/5/21 20:20
 */
public class ResultBean<T> {

    // 状态码
    private Integer code;

    private String message;

    private Long timestamp;


    private static final Integer SUCCESS_CODE = 200;

    private static final Integer FAIL_CODE = 500;

    // 成功提示消息
    private static String successMsg = "success";

    // 失败提示消息
    private static String failMsg = "Failed";

    // 当前时间戳
    private static Long currentTimeMillis = System.currentTimeMillis();

    // 返回数据
    private T data;

    public ResultBean() {
    }

    public ResultBean(Integer code, Long timestamp, String message, T data) {
        this.code = code;
        this.timestamp = timestamp;
        this.data = data;
        this.message = message;
    }

    public static <T> ResultBean<T> error(ResultBean<?> result) {
        return error(result.getCode(), result.getMessage());
    }

    public static <T> ResultBean<T> error(T data) {
        return new ResultBean<>(FAIL_CODE, currentTimeMillis, failMsg, data);
    }

    public static <T> ResultBean<T> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> ResultBean<T> error(Integer code, String message) {
        Assert.isTrue(!SUCCESS_CODE.equals(code), "状态码必须是错误的!");
        return new ResultBean<>(code, currentTimeMillis, message, null);
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<>(SUCCESS_CODE, currentTimeMillis, successMsg, data);
    }

    public static <T> ResultBean<T> error(Throwable e) {
        return new ResultBean<>(FAIL_CODE, currentTimeMillis, e.getMessage(), null);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}
