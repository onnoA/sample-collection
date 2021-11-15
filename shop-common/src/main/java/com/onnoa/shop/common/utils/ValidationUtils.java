package com.onnoa.shop.common.utils;


import com.onnoa.shop.common.exception.ServiceException;
import com.onnoa.shop.common.exception.SysErrorCodeEnum;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

public class ValidationUtils {

    private static javax.validation.Validator validator = Validation.
            byProvider(HibernateValidator.class)
            .configure()
            .buildValidatorFactory()
            .getValidator();

    /**
     * @param obj 需要验证的对象
     * @catalog hibernate
     * @title hibernate注解验证参数
     * @description 使用hibernate注解验证类中参数的合法性
     */
    public static <T> void validate(T obj) {
        if (obj == null) {
            throw new ServiceException(SysErrorCodeEnum.COMMON_PARAMS_IS_ILLICIT.getCode(), "参数实体不能为空");
        }
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        // 抛出检验异常
        if (constraintViolations.size() > 0) {
            StringBuffer sb = new StringBuffer();
            constraintViolations.stream().forEach(violation -> {
                sb.append(violation.getPropertyPath())
                        .append(violation.getMessage())
                        .append(",");
            });
            sb.deleteCharAt(sb.length() - 1);
            throw new ServiceException(SysErrorCodeEnum.COMMON_PARAMS_IS_ILLICIT.getCode(),
                    String.format("参数校验失败:%s", sb.toString()));
        }
    }
}
