package com.onnoa.spring.configuration.metadata.config.utils;

import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReflectUtil {

    public static String getAttrName(Method attribute) {
        return attribute.getName();
    }

    /**
     * 获取类的属性及属性值集合
     *
     * @return
     */
    public static <T> /*Map<String, Object>*/ void getObjAttrValue(Class<T> object) throws Exception {
        Map<String, Object> objMap = Maps.newHashMap();
        //我们项目的所有实体类都继承BaseDomain （所有实体基类：该类只是串行化一下）
        //不需要的自己去掉即可
//        if (object != null && object instanceof BaseDomain) {//if (object!=null )  ----begin
        // 拿到该类
//        Class<?> clz = object;
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = object.getDeclaredFields();

        for (Field field : fields) {
            System.out.println(field.getGenericType());//打印该类的所有属性类型

            // 如果类型是String
            if (field.getGenericType().toString().equals(
                    "class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                // 拿到该属性的gettet方法
                /**
                 * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
                 * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
                 * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
                 */
                Method m = (Method) object.getMethod("get" + getMethodName(field.getName()));
//                String name = field.getName();
                String val = (String) m.invoke(object);// 调用getter方法获取属性值
                if (val != null) {
                    System.out.println("String type:" + val);
                }

            }

            // 如果类型是Integer
            if (field.getGenericType().toString().equals(
                    "class java.lang.Integer")) {
                Method m = (Method) object.getMethod(
                        "get" + getMethodName(field.getName()));
                Integer val = (Integer) m.invoke(object);
                if (val != null) {
                    System.out.println("Integer type:" + val);
                }

            }

            // 如果类型是Double
            if (field.getGenericType().toString().equals(
                    "class java.lang.Double")) {
                Method m = (Method) object.getMethod(
                        "get" + getMethodName(field.getName()));
                Double val = (Double) m.invoke(object);
                if (val != null) {
                    System.out.println("Double type:" + val);
                }

            }

            // 如果类型是Boolean 是封装类
            if (field.getGenericType().toString().equals(
                    "class java.lang.Boolean")) {
                Method m = (Method) object.getMethod(
                        field.getName());
                Boolean val = (Boolean) m.invoke(object);
                if (val != null) {
                    System.out.println("Boolean type:" + val);
                }

            }

            // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
            // 反射找不到getter的具体名
            if (field.getGenericType().toString().equals("boolean")) {
                Method m = (Method) object.getMethod(
                        field.getName());
                Boolean val = (Boolean) m.invoke(object);
                if (val != null) {
                    System.out.println("boolean type:" + val);
                }

            }
            // 如果类型是Date
            if (field.getGenericType().toString().equals(
                    "class java.util.Date")) {
                Method m = (Method) object.getMethod(
                        "get" + getMethodName(field.getName()));
                Date val = (Date) m.invoke(object);
                if (val != null) {
                    System.out.println("Date type:" + val);
                }

            }
            // 如果类型是Short
            if (field.getGenericType().toString().equals(
                    "class java.lang.Short")) {
                Method m = (Method) object.getMethod(
                        "get" + getMethodName(field.getName()));
                Short val = (Short) m.invoke(object);
                if (val != null) {
                    System.out.println("Short type:" + val);
                }

            }
            // 如果还需要其他的类型请自己做扩展
        }
    }

    public static void getObjectValue(Object object) throws Exception {
        //我们项目的所有实体类都继承BaseDomain （所有实体基类：该类只是串行化一下）
        //不需要的自己去掉即可
        if (object != null) {//if (object!=null )  ----begin
            // 拿到该类
            Class<?> clz = object.getClass();
            // 获取实体类的所有属性，返回Field数组
            Field[] fields = clz.getDeclaredFields();

            for (Field field : fields) {// --for() begin
                System.out.println(field.getGenericType());//打印该类的所有属性类型

                // 如果类型是String
                if (field.getGenericType().toString().equals(
                        "class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    // 拿到该属性的gettet方法
                    /**
                     * 这里需要说明一下：他是根据拼凑的字符来找你写的getter方法的
                     * 在Boolean值的时候是isXXX（默认使用ide生成getter的都是isXXX）
                     * 如果出现NoSuchMethod异常 就说明它找不到那个gettet方法 需要做个规范
                     */
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));

                    String val = (String) m.invoke(object);// 调用getter方法获取属性值
                    if (val != null) {
                        System.out.println("String type:" + val);
                    }

                }

                // 如果类型是Integer
                if (field.getGenericType().toString().equals(
                        "class java.lang.Integer")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Integer val = (Integer) m.invoke(object);
                    if (val != null) {
                        System.out.println("Integer type:" + val);
                    }

                }

                // 如果类型是Double
                if (field.getGenericType().toString().equals(
                        "class java.lang.Double")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Double val = (Double) m.invoke(object);
                    if (val != null) {
                        System.out.println("Double type:" + val);
                    }

                }

                // 如果类型是Boolean 是封装类
                if (field.getGenericType().toString().equals(
                        "class java.lang.Boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        System.out.println("Boolean type:" + val);
                    }

                }

                // 如果类型是boolean 基本数据类型不一样 这里有点说名如果定义名是 isXXX的 那就全都是isXXX的
                // 反射找不到getter的具体名
                if (field.getGenericType().toString().equals("boolean")) {
                    Method m = (Method) object.getClass().getMethod(
                            field.getName());
                    Boolean val = (Boolean) m.invoke(object);
                    if (val != null) {
                        System.out.println("boolean type:" + val);
                    }

                }
                // 如果类型是Date
                if (field.getGenericType().toString().equals(
                        "class java.util.Date")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Date val = (Date) m.invoke(object);
                    if (val != null) {
                        System.out.println("Date type:" + val);
                    }

                }
                // 如果类型是Short
                if (field.getGenericType().toString().equals(
                        "class java.lang.Short")) {
                    Method m = (Method) object.getClass().getMethod(
                            "get" + getMethodName(field.getName()));
                    Short val = (Short) m.invoke(object);
                    if (val != null) {
                        System.out.println("Short type:" + val);
                    }

                }
                // 如果还需要其他的类型请自己做扩展

            }//for() --end

        }//if (object!=null )  ----end
    }


    // 把一个字符串的第一个字母大写、效率是最高的、
    private static String getMethodName(String filedName) {
        byte[] items = filedName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


    private int getValueHashCode(Object value) {
        if (value instanceof boolean[]) {
            return Arrays.hashCode((boolean[]) value);
        }
        if (value instanceof byte[]) {
            return Arrays.hashCode((byte[]) value);
        }
        if (value instanceof char[]) {
            return Arrays.hashCode((char[]) value);
        }
        if (value instanceof double[]) {
            return Arrays.hashCode((double[]) value);
        }
        if (value instanceof float[]) {
            return Arrays.hashCode((float[]) value);
        }
        if (value instanceof int[]) {
            return Arrays.hashCode((int[]) value);
        }
        if (value instanceof long[]) {
            return Arrays.hashCode((long[]) value);
        }
        if (value instanceof short[]) {
            return Arrays.hashCode((short[]) value);
        }
        if (value instanceof Object[]) {
            return Arrays.hashCode((Object[]) value);
        }
        return value.hashCode();
    }

}
