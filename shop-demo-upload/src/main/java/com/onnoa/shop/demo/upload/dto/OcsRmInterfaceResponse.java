package com.onnoa.shop.demo.upload.dto;

import lombok.ToString;

import javax.validation.Valid;

@ToString
public class OcsRmInterfaceResponse<T> {
    /**
     * 编码
     */
    private Integer code;

    /**
     * 提示信息, 报错为报错信息，其他为接口返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    @Valid
    private T data;

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
}
