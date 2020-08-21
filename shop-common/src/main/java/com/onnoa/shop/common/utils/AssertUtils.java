package com.onnoa.shop.common.utils;

import com.onnoa.shop.common.exception.ServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public final class AssertUtils {

    /**
     * 断言表达式为true
     *
     * @param expression 需要断言的表达式
     * @param exception  断言业务异常
     */
    public static <T extends ServiceException> void mustTrue(boolean expression, T exception) {
        if (!expression) {
            throw exception;
        }
    }

    /**
     * 断言表达式为fasle
     *
     * @param expression 需要断言的表达式
     * @param exception  断言业务异常
     */
    public static <T extends ServiceException> void mustFalse(boolean expression, T exception) {
        if (expression) {
            throw exception;
        }
    }

    /**
     * 断言对象为null
     *
     * @param object    需要断言的对象
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNull(Object object, T exception) {
        if (object != null) {
            throw exception;
        }
    }

    /**
     * 断言对象不能为null
     *
     * @param object    需要断言的对象
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotNull(Object object, T exception) {
        if (object == null) {
            throw exception;
        }
    }

    /**
     * 断言对象不能为null
     *
     * @param objects   需要断言的对象
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotNull(Object[] objects, T exception) {
        if (objects == null) {
            throw exception;
        }
        if (Arrays.stream(objects).anyMatch(Objects::isNull)) {
            throw exception;
        }
    }

    /**
     * 断言字符串为空
     *
     * @param text      需要断言的字符串
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustEmpty(String text, T exception) {
        if (StringUtils.isNotEmpty(text)) {
            throw exception;
        }
    }

    /**
     * 断言字符串不能为空
     *
     * @param text      需要断言的字符串
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotEmpty(String text, T exception) {
        if (StringUtils.isEmpty(text)) {
            throw exception;
        }
    }

    /**
     * 断言字符串不能为null、不能为空、不能是空字符串
     *
     * @param text      需要断言的字符串
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotBlank(String text, T exception) {
        if (StringUtils.isBlank(text)) {
            throw exception;
        }
    }

    /**
     * 断言数组不能为空
     *
     * @param array     需要断言的数组
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotEmpty(Object[] array, T exception) {
        if (ArrayUtils.isEmpty(array)) {
            throw exception;
        }
    }

    /**
     * 断言集合不能为空
     *
     * @param collection 需要断言的集合
     * @param exception  断言业务异常
     */
    public static <T extends ServiceException> void mustNotEmpty(Collection<?> collection, T exception) {
        if (CollectionUtils.isEmpty(collection)) {
            throw exception;
        }
    }

    /**
     * 断言集合不能为空
     *
     * @param collection 需要断言的集合
     * @param exception  断言业务异常
     */
    public static <T extends ServiceException> void mustEmpty(Collection<?> collection, T exception) {
        if (CollectionUtils.isNotEmpty(collection)) {
            throw exception;
        }
    }

    /**
     * 断言映射不能为空
     *
     * @param map       需要断言的映射
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotEmpty(Map<?, ?> map, T exception) {
        if (map == null || map.isEmpty()) {
            throw exception;
        }
    }

    /**
     * 断言对象是某个类的实例
     *
     * @param type      需要断言的类
     * @param obj       需要断言的实例
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustInstanceOf(Class<?> type, Object obj, T exception) {
        if (!type.isInstance(obj)) {
            throw exception;
        }
    }

    /**
     * 断言某个类是另外一个类的子类
     *
     * @param superType 需要断言的父类
     * @param subType   需要断言的子类
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustAssignable(Class<?> superType, Class<?> subType, T exception) {
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw exception;
        }
    }

    /**
     * 断言包装类型为非空并且非零
     *
     * @param value     需要断言的表达式
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotNullAndZero(Integer value, T exception) {
        if (value == null || value == 0) {
            throw exception;
        }
    }

    /**
     * 断言包装类型为非空并且非零
     *
     * @param value     需要断言的表达式
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotNullAndZero(Long value, T exception) {
        if (value == null || value == 0) {
            throw exception;
        }
    }

    /**
     * 断言包装类型为非空并且非零
     *
     * @param value     需要断言的表达式
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotNullAndZero(Short value, T exception) {
        if (value == null || value == 0) {
            throw exception;
        }
    }

    /**
     * 断言包装类型为非空并且非零
     *
     * @param value     需要断言的表达式
     * @param exception 断言业务异常
     */
    public static <T extends ServiceException> void mustNotNullAndZero(BigDecimal value, T exception) {
        if (value == null || new BigDecimal(0).equals(value)) {
            throw exception;
        }
    }

}
