package com.onnoa.shop.common.global;

import com.onnoa.shop.common.exception.ServiceException;
import com.onnoa.shop.common.exception.ServiceExceptionUtil;
import com.onnoa.shop.common.exception.SysErrorCodeEnum;
import com.onnoa.shop.common.result.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 全局异常处理
 * @Author: onnoA
 * @Date: 2020/5/26 15:44
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 功能描述: 自定义异常处理
     *
     * @param e
     * @return
     * @date 2020/6/5 15:50
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseBody
    public ResultBean handleServiceException(ServiceException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("业务异常：", e);
        }
        return ResultBean.error(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResultBean handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        StringBuffer sb = new StringBuffer();
        for (FieldError fieldError : fieldErrors) {
            sb.append("'").append(fieldError.getField()).append("'").append(fieldError.getDefaultMessage()).append("\r\n");
        }
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数绑定异常：", e);
        }
        return ResultBean.error(SysErrorCodeEnum.COMMON_PARAMS_IS_ILLICIT.getCode(), sb.toString());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResultBean handleConstraintViolationException(ConstraintViolationException e) {
        if (LOGGER.isErrorEnabled()) {
            LOGGER.error("参数校验异常：", e);
        }
        return ResultBean.error(SysErrorCodeEnum.COMMON_PARAMS_IS_ILLICIT.getCode(), e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("，")));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultBean handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.error("参数校验失败={}", e.getMessage(), e);

        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String collect = allErrors.stream().map(ex -> ex.getObjectName() + ":" + ex.getDefaultMessage()).collect(Collectors.joining(";"));
        LOGGER.error("参数校验返回异常信息:{}",collect);
        // 然后提取错误提示信息进行返回

        List<FieldError> bindingResult = e.getBindingResult().getFieldErrors();
        return ResultBean.error(SysErrorCodeEnum.COMMON_PARAMS_IS_ILLICIT.getCode(), bindingResult.stream().map(ex -> ex.getField() + ":" + ex.getDefaultMessage()).collect(Collectors.joining("，")));
    }

    /**
     * 功能描述: 全局未处理的异常.
     *
     * @param e
     * @return
     * @date 2020/5/26 16:00
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResultBean exception(Exception e) {
        LOGGER.error("系统异常：{}", e.getMessage(), e);
        return ResultBean.error(SysErrorCodeEnum.SYSTEM_GATEWAY_ERROR.getCode(), SysErrorCodeEnum.SYSTEM_GATEWAY_ERROR.getMessage() + e.getMessage() + ":" + e);
    }

}
