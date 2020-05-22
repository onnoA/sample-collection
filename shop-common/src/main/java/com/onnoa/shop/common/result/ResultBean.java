package com.onnoa.shop.common.result;

import org.springframework.util.Assert;

/**
 * @Description: 包装通用的返回结果
 * @Author: onnoA
 * @Date: 2020/5/21 20:20
 */
public class ResultBean<T> {


    private static final Integer SUCCESS_CODE = 200;

    private static final Integer FAIL_CODE = 500;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private Long timestamp = System.currentTimeMillis();

    /**
     * 提示消息
     */
    private static String message = "success";

    /**
     * 返回数据
     */
    private T data;

    public static <T> ResultBean<T> error(ResultBean<?> result) {
        return error(result.getCode(), result.getMessage());
    }

    public static <T> ResultBean<T> error(Integer code, String message) {
        Assert.isTrue(!SUCCESS_CODE.equals(code), "状态码必须是错误的!");
        ResultBean<T> result = new ResultBean<>();
        result.code = code;
        result.message = message;
        return result;
    }

    public static <T> ResultBean<T> success(T data) {
        ResultBean<T> result = new ResultBean<>();
        result.code = SUCCESS_CODE;
        result.message = message;
        result.data = data;
        return result;
    }

    public static <T> ResultBean<T> error(Throwable e) {
        ResultBean<T> result = new ResultBean<>();
        result.code = FAIL_CODE;
        result.message = e.getMessage();
        return result;
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
                ", data=" + data +
                '}';
    }
}
