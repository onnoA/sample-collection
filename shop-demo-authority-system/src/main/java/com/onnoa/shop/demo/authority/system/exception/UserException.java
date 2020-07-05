package com.onnoa.shop.demo.authority.system.exception;

import com.onnoa.shop.common.exception.ServiceException;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/4 15:12
 */
public class UserException extends ServiceException {

    public static final ServiceException USER_NOT_EXITS = new ServiceException(400010001, "用户不存在！");



    protected int code;
    protected String msg;

    protected UserException(Integer code, String msg) {
        super(code, msg);
    }


    @Override
    public String toString() {
        return String.format("业务异常=%s, code=%s, msg=%s", this.getClass().getSimpleName(), this.code,
                this.msg);
    }
}
