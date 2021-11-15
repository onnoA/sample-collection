package com.onnoa.shop.demo.upload.exception;

import com.onnoa.shop.common.exception.ServiceException;

public class FileException extends ServiceException {

    public static final FileException FILE_UPLOAD_FAILED = new FileException(400010001, "文件上传失败,文件名 : {}");
    public static final FileException FILE_NOT_EXIST = new FileException(400010002, "文件不存在,文件名 :{}");

    public FileException(String errMsg) {
        super(errMsg);
    }

    protected FileException(Integer code, String msg) {
        super(code, msg);
    }

    @Override
    public String toString() {
        return String.format("业务异常=%s, code=%s, msg=%s", this.getClass().getSimpleName(), this.code,
                this.msg);
    }
}
