package com.onnoa.shop.common.exception;

public class FileException extends ServiceException {





    protected int code;
    protected String msg;

    protected FileException(Integer code, String msg) {
        super(code, msg);
    }

    @Override
    public String toString() {
        return String.format("业务异常=%s, code=%s, msg=%s", this.getClass().getSimpleName(), this.code,
                this.msg);
    }
}
