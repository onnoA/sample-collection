package com.onnoa.shop.common.exception;

import com.onnoa.shop.common.result.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: {@link ServiceException} 工具类
 * @Author: onnoA
 * @Date: 2020/4/21 10:42
 */
public class ServiceExceptionUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionUtil.class);

    private static Map<Integer, String> messages = new ConcurrentHashMap<>();

    public static void pullAll(Map<Integer, String> message) {
        ServiceExceptionUtil.messages.putAll(message);
    }

    public static void put(Integer code, String message) {
        ServiceExceptionUtil.messages.put(code, message);
    }

    public static <T> ResultBean<T> error(Integer code) {
        return ResultBean.error(code, messages.get(code));
    }

    public static <T> ResultBean<T> error(SysErrorCodeEnum codeEnum) {
        return ResultBean.error(codeEnum.getCode(), codeEnum.getMessage());
    }

    public static <T> ResultBean<T> error(ErrorCode errorCode) {
        return ResultBean.error(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultBean error(Integer code, Object... params) {
        String message = doFormat(code, messages.get(code), params);
        return ResultBean.error(code, message);
    }

    /**
     * 创建指定编号的 ServiceException 的异常
     *
     * @param code 编号
     * @return 异常
     */
    public static ServiceException exception(Integer code) {
        return new ServiceException(code, messages.get(code));
    }

    /**
     * 创建指定编号的 ServiceException 的异常
     *
     * @param code   编号
     * @param params 消息提示的占位符对应的参数
     * @return 异常
     */
    public static ServiceException exception(Integer code, Object... params) {
        String message = doFormat(code, messages.get(code), params);
        return new ServiceException(code, message);
    }

    public static ServiceException exception(Integer code, String messagePattern, Object... params) {
        String message = doFormat(code, messagePattern, params);
        return new ServiceException(code, message);
    }

    private static String doFormat(Integer code, String messagePattern, Object... params) {
        StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
        int i = 0;
        int j;
        int l;
        for (l = 0; l < params.length; l++) {
            j = messagePattern.indexOf("{}", i);
            if (j == -1) {
                LOGGER.error("[doFormat][参数过多：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
                if (i == 0) {
                    return messagePattern;
                } else {
                    sbuf.append(messagePattern.substring(i, messagePattern.length()));
                    return sbuf.toString();
                }
            } else {
                sbuf.append(messagePattern.substring(i, j));
                sbuf.append(params[l]);
                i = j + 2;
            }
        }
        if (messagePattern.indexOf("{}", i) != -1) {
            LOGGER.error("[doFormat][参数过少：错误码({})|错误内容({})|参数({})", code, messagePattern, params);
        }
        sbuf.append(messagePattern.substring(i, messagePattern.length()));
        return sbuf.toString();
    }

}
