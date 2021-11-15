package com.onnoa.shop.common.exception;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

/**
 * @Description: 服务异常
 * 参考 https://www.kancloud.cn/onebase/ob/484204 文章
 * 10 位状态码，分成四段
 * 第一段，1 位，类型
 * 1 - 业务级别异常
 * 2 - 系统级别异常
 * 第二段，3 位，系统类型
 * 000 - 通用模块
 * 001 - 用户系统
 * 002 - 商品系统
 * 003 - 订单系统
 * 004 - 支付系统
 * 005 - 优惠劵系统
 * ... - ...
 * 第三段，3 位，模块
 * 不限制规则。
 * 一般建议，每个系统里面，可能有多个模块，可以再去做分段。以用户系统为例子：
 * 000 - 通用检验模块
 * 001 - OAuth2 模块
 * 002 - User 模块
 * 003 - MobileCode 模块
 * 第四段，3 位，错误码
 * 不限制规则。
 * 一般建议，每个模块自增。
 * @Author: onnoA
 * @Date: 2020/4/21 10:37
 */
public class ServiceException extends RuntimeException implements Serializable {

    public static final ServiceException SYS_ERROR = new ServiceException(999900000, "系统异常：{0}");

    public static final ServiceException NEW_EXCEPTION_INSTANCE_FAILED = new ServiceException(999900001, "创建业务异常新实例失败。");

    public static final ServiceException COMMON_PARAMS_NOT_NULL = new ServiceException(999900002, "参数{0}不能为空。。");

    public static final ServiceException OBJECT_IS_NOT_EXIST = new ServiceException(999900003, "对象信息不存在：{0}");

    public static final ServiceException DATA_INVALID = new ServiceException(999900004, "{0}");

    public static final ServiceException SMS_SEND_FAIL = new ServiceException(999900005, "短信发送失败，{0}");

    public static final ServiceException SMS_CODE_HAS_NOT_EXPIRED = new ServiceException(999900006, "该短信验证码尚未过期，请勿重复操作。");

    public static final ServiceException SMS_CODE_HAS_EXPIRED = new ServiceException(999900007, "该短信验证码已过期，请重新获取验证码。");

    public static final ServiceException SMS_CODE_ERROR = new ServiceException(999900008, "您输入的验证码有误，请重新输入。");

    public static final ServiceException PROFILE_NOT_EXIST = new ServiceException(999900009, "该配置文件不存在，文件名: {0}。");

    public static final ServiceException FILE_UPLOAD_FAILED = new ServiceException(999900010, "文件上传失败，文件名: {0} 。");

    public static final ServiceException FILE_TO_GET_CONNECTION = new ServiceException(999900011, "获取 {0} 连接失败。");


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        ServiceException serviceException = new ServiceException(null);
        serviceException.test();

    }

    public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<? extends ServiceException> constructor = this.getClass().getDeclaredConstructor(new Class[]{Integer.class, String.class});
        constructor.setAccessible(true);
        ServiceException serviceException = constructor.newInstance();
        Integer code = serviceException.getCode();

    }


    protected int code;
    protected String msg;


    public ServiceException(String errMsg) {
        super(errMsg);
    }

    public ServiceException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg == null ? "" : msg;
    }

    public ServiceException(String errMsg, Throwable e) {
        super(errMsg, e);
    }

    public Integer getCode() {
        return code;
    }


    /***
     * 格式话消息.
     *
     * @param args
     * @return
     */
    public ServiceException format(Object... args) {
        try {
            String message = MessageFormat.format(this.msg, args);
            return new ServiceException(this.code, message);
        } catch (Exception e) {
            throw NEW_EXCEPTION_INSTANCE_FAILED.newInstance("创建业务异常新实例失败" + e.toString());
        }
    }

    /**
     * 创建新实例。
     *
     * @param msg 覆盖原有消息的新消息
     * @return
     */
    public ServiceException newInstance(String msg) {
        try {
            Class<?> clazz = this.getClass();
            Constructor<?> constructor;
            constructor = clazz.getDeclaredConstructor(new Class[]{int.class, String.class});
            constructor.setAccessible(true);
            //ServiceException se;
            return (ServiceException) constructor.newInstance(this.code, msg);
        } catch (Exception e) {
            throw NEW_EXCEPTION_INSTANCE_FAILED.newInstance("创建业务异常新实例失败" + e.toString());
        }
    }

}
