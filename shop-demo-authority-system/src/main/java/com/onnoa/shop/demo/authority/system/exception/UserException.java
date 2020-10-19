package com.onnoa.shop.demo.authority.system.exception;

import com.onnoa.shop.common.exception.ServiceException;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/6/4 15:12
 */
public class UserException extends ServiceException {

    public static final UserException USER_NOT_EXITS = new UserException(400010001, "用户不存在,用户名为:{0}！");
    public static final UserException USER_VERIFY_CODE_FAILURE = new UserException(400010002, "用户验证码失效！");
    public static final UserException USER_VERIFY_CODE_ERROR = new UserException(400010003, "用户验证码错误！");
    public static final UserException USER_PASSWORD_WRONG = new UserException(400010004, "用户密码错误！");
    public static final UserException ACCESS_TOKEN_HAS_EXPIRED = new UserException(400010005, "accessToken已过期！");
    public static final UserException USERID_AUTH_NOT_PASSED = new UserException(400010006, "用户ID验证不通过！");
    public static final UserException USER_HAS_NOT_PERMISSION = new UserException(400010007, "用户没有该权限！");
    public static final UserException DATA_INVALID = new UserException(400010008, "{0}");
    public static final UserException OBJECT_IS_NULL = new UserException(400010009, "参数不能为空!");

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
