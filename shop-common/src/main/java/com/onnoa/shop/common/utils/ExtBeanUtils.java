package com.onnoa.shop.common.utils;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ExtBeanUtils extends BeanUtils {

    private static Logger logger = LoggerFactory.getLogger(ExtBeanUtils.class);

    public static <T> T copyToNewBean(Object source, Class<T> valueType) {
        T target = BeanUtils.instantiate(valueType);
        copyProperties(source, target, true);
        return target;
    }

    public static <T> T mapToBean(Map<String, ? extends Object> properties, Class<T> clazz) {
        if (properties == null || properties.isEmpty()) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(properties, clazz);
    }

    public static <R, S> void copyBeanList(List<S> sourceList, List<R> returnList, Class<R> clazz) {
        if (clazz == null) {
            throw new NullPointerException("传入的返回值类型不能为空");
        } else if (sourceList != null && returnList != null) {
            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Iterator var4 = sourceList.iterator();
                while (var4.hasNext()) {
                    S s = (S) var4.next();
                    R target = (R) constructor.newInstance();
                    copyProperties(s, target);
                    returnList.add(target);
                }
            } catch (Exception var7) {
                throw new RuntimeException("转化过程异常");
            }
        }
    }

    public static Map<String, Object> beanToMapInFields(Object obj, List<String> inFields) {
        if (obj == null) {
            return null;
        } else {
            Map<String, Object> objectAsMap = beanToMap(obj);
            Map<String, Object> result = new HashMap();
            if (inFields != null) {
                Iterator var4 = inFields.iterator();

                while (var4.hasNext()) {
                    String fieldName = (String) var4.next();
                    if (objectAsMap.containsKey(fieldName)) {
                        result.put(fieldName, objectAsMap.get(fieldName));
                    }
                }
            }

            return result;
        }
    }

    public static Map<String, Object> beanToMap(Object obj) {
        if (obj == null) {
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            return (Map) objectMapper.convertValue(obj, Map.class);
        }
    }

    public static void copyProperties(Object source, Object target, Boolean isCopyNull)
            throws BeansException {
        if (isCopyNull) {
            copyProperties(source, target);
        }
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                        if (value != null) {
                            Method writeMethod = targetPd.getWriteMethod();
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            writeMethod.invoke(target, value);
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties from source to target", ex);
                    }
                }
            }
        }
    }

    public static Map<String, String> beanToStringMap(Object target) {
        if (target == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        Class<?> actualEditable = target.getClass();
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor targetPd : targetPds) {
            try {
                Method readMethod = targetPd.getReadMethod();
                if (Object.class.equals(readMethod.getDeclaringClass())) {
                    continue;
                }
                Object value = readMethod.invoke(target);
                if (value != null) {
                    if (value instanceof String) {
                        if (StringUtils.hasText((String) value)) {
                            map.put(targetPd.getName(), value.toString());
                        }
                    } else {
                        map.put(targetPd.getName(), value.toString());
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return map;
    }
}
