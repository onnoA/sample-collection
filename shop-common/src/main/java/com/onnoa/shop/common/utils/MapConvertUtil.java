package com.onnoa.shop.common.utils;

import com.onnoa.shop.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Map转换工具类
 */
public class MapConvertUtil {

    private static Logger Logger = LoggerFactory.getLogger(MapConvertUtil.class);


    public static List mapListToObjectList(List<Map> mapList, Class<?> objectClass) {
        if (mapList == null || mapList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List objectList = new ArrayList();
        for (Map map : mapList) {
            objectList.add(mapToObject(map, objectClass));
        }
        return objectList;
    }

    /**
     * 对象转MAP
     *
     * @param input
     * @return
     */
    public static Map objectToMap(Object input) {
        Map result = new HashMap();
        Field[] fields = input.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(input);
                result.put(field.getName(), value);
            }
            catch (IllegalAccessException e) {
                Logger.error(e.getMessage(), e);
                throw new ServiceException(e.getMessage());
            }
        }
        return result;

    }

    /**
     * MAP 类型转换对象
     *
     * @param map
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) {
        T obj = null;
        if (map == null) {
            return null;
        }
        try {
            obj = beanClass.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            Map<String, Boolean> insertedValue = new HashMap<>(); // 已经插入的部分，不需要调用get/set方法
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                Class cl = field.getType();

                if (map.get(field.getName()) != null && !"".equals(map.get(field.getName()))
                        && StringUtils.isNotBlank(String.valueOf(map.get(field.getName())))) {
                    if (cl.getName().equals("java.lang.Long") || cl.getName().equals("long")) {
                        Object o = map.get(field.getName());
                        if (o instanceof String) {
                            if (o.equals("null")) {
                                //
                            }
                            else {
                                field.set(obj, Long.parseLong((String) o));
                            }

                        }
                        else {
                            field.set(obj, Long.parseLong(o.toString()));
                        }

                    }
                    else if (cl.getName().equals("java.lang.Integer") || cl.getName().equals("int")) {
                        Object o = map.get(field.getName());
                        if (o instanceof String) {
                            field.set(obj, Integer.parseInt((String) o));
                        }
                        else if (o instanceof Double) {
                            // field.set(obj,(int) Double.parseDouble((String) o));
                            field.set(obj, (int) ((Double) o).doubleValue());
                        }
                        else {
                            field.set(obj, Integer.parseInt(o.toString()));
                        }

                    }
                    else if (cl.getName().equals("java.util.Date") || cl.getName().equals("Date")) {
                        Object o = map.get(field.getName());

                        if (o instanceof java.lang.Long) {
                            field.set(obj, new Date(Long.parseLong(o.toString())));
                        }

                        if (o instanceof java.lang.String) {
                            try {
                                field.set(obj, DateUtils.getFormatedDateTime(String.valueOf(o)));
                            }
                            catch (Exception e) {
                                field.set(obj, DateUtils.getFormatedDate(String.valueOf(o)));
                            }

                        }
                    }
                    else if (cl.getName().equals("java.lang.String")) {
                        Object o = map.get(field.getName());
                        String str = "";
                        if (o != null) {
                            str = String.valueOf(o);
                        }
                        field.set(obj, str);
                    }
                    else {
                        field.set(obj, map.get(field.getName()));
                    }
                    insertedValue.put(field.getName(), true);
                }

            }

            // 调用set的方法，设置父类的属性.
            Method[] methods = obj.getClass().getMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (!methodName.startsWith("get")) {
                    continue;
                }
                String currentFieldName = methodName.substring(3, methodName.length());
                currentFieldName = currentFieldName.substring(0, 1).toLowerCase()
                        + currentFieldName.substring(1, currentFieldName.length());
                if (insertedValue.get(currentFieldName) != null || map.get(currentFieldName) == null) {
                    continue; // 已经在上面进行处理,获取属性不存在
                }
                // 获取set方法
                Method setMethod = getSetMethodByName(methods, currentFieldName);
                if (setMethod == null) {
                    continue;
                }

                // 获取字段类型，进行转换
                doSetMethodValue(setMethod, map.get(currentFieldName), obj);

            }

        }
        catch (Exception e) {
            Logger.error(e.getMessage(), e);
            throw new ServiceException(e.getMessage(), e);
        }

        return obj;
    }

    /**
     * 获取SET方法
     *
     * @param methods
     * @param name
     * @return
     */
    private static Method getSetMethodByName(Method[] methods, String name) {
        String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        return null;

    }

    /**
     * 调用SET方法复制
     *
     * @param setMethod
     * @param setValue
     * @param desObject
     * @throws Exception
     */
    private static void doSetMethodValue(Method setMethod, Object setValue, Object desObject) throws InvocationTargetException, IllegalAccessException {
        Class type = setMethod.getParameterTypes()[0];
        if (type.getName().equals("java.lang.Long") || type.getName().equals("long")) {
            Long longVal = 0l;
            try {
                if (setValue instanceof String) {
                    longVal = Long.parseLong((String) setValue);
                }
                else {
                    longVal = Long.parseLong(setValue.toString());
                }
            }
            catch (Exception e) {
                //
            }
            setMethod.invoke(desObject, longVal);

        }
        else if (type.getName().equals("java.lang.Integer") || type.getName().equals("int")) {
            Integer intVal = 0;
            try {
                if (setValue instanceof String) {
                    intVal = Integer.parseInt((String) setValue);
                }
                else {
                    intVal = Integer.parseInt(setValue.toString());
                }

            }
            catch (Exception e) {
                //
            }
            setMethod.invoke(desObject, intVal);

        }
        else if (type.getName().equals("java.util.Date") || type.getName().equals("Date")) {
            Date dateValue = null;
            if (setValue != null && StringUtils.isNotEmpty(String.valueOf(setValue))) {
                try {
                    if (setValue instanceof Date) {
                        dateValue = (Date) setValue;
                    }
                    else if (setValue instanceof Long || setValue.getClass().getName().equals(long.class.getName())) {
                        dateValue = new Date((long) setValue);
                    }
                }
                catch (Exception e) {
                    //
                }
            }
            setMethod.invoke(desObject, dateValue);
        }
        else {
            setMethod.invoke(desObject, setValue);

        }

    }

    public static String getStringValue(Map paramMap, String key) {
        Object value = paramMap.get(key);
        if (value == null) {
            return "";
        }
        if (value instanceof String) {
            return (String) value;
        }
        else {
            return value.toString();
        }
    }

    public static Integer getIntValue(Map paramMap, String key) {
        Object value = paramMap.get(key);
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        else if (value instanceof String) {
            return Integer.valueOf((String) value);
        }
        return 0;
    }

    public static Long getLongValue(Map paramMap, String key) {
        Object value = paramMap.get(key);
        if (value == null) {
            return 0l;
        }
        if (value instanceof Long) {
            return (Long) value;
        }
        else if (value instanceof Integer) {
            return Long.valueOf(((Integer) value).longValue());
        }
        else if (value instanceof String) {
            return Long.valueOf((String) value);
        }
        return 0l;
    }

    public static Double getDoubleValue(Map paramMap, String key) {
        Object value = paramMap.get(key);
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Double) {
            return (Double) value;
        }
        else if (value instanceof String) {
            return Double.valueOf((String) value);
        }
        return 0.0;
    }

    /**
     * 对象转MAP
     *
     * @param input
     * @return
     */
    public static Map objectToMapIgnoreValue(Object input) {
        Map result = new HashMap();
        Field[] fields = input.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(input);
                if (value != null) {
                    result.put(field.getName(), value);
                }
            }
            catch (IllegalAccessException e) {
                Logger.error(e.getMessage(), e);
                throw new ServiceException(e.getMessage());
            }
        }
        return result;

    }

    public static void removeNullKey(Map map) {
        if (map != null) {
            Iterator<Map.Entry> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = iterator.next();
                if (entry.getValue() == null) {
                    iterator.remove();
                }
            }
        }
    }
}
