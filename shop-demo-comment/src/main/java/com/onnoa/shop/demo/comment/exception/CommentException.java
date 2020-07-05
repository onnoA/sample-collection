package com.onnoa.shop.demo.comment.exception;

import com.onnoa.shop.common.exception.ServiceException;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/4 15:12
 */
public class CommentException extends ServiceException {

    public static final ServiceException VIDEO_FORMAT_ABNORMAL = new ServiceException(300010001, "暂不支持:{}格式的视频。");



    protected int code;
    protected String msg;

    protected CommentException(Integer code, String msg) {
        super(code, msg);
    }


    @Override
    public String toString() {
        return String.format("业务异常=%s, code=%s, msg=%s", this.getClass().getSimpleName(), this.code,
                this.msg);
    }
}
