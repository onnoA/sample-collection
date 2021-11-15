package com.onnoa.shop.common.utils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: ObjectToBeanUtils
 * @description:
 * @author: onnoA
 * @date: 2021/10/9
 **/
public class ObjectToBeanUtils {

    /**
     * List<Object[]>转换成List<T>
     */
    public static <T> List<T> objectToBean(List<Object[]> objList, Class<T> clz) throws Exception {
        if (objList == null || objList.size() == 0) {
            return null;
        }

        Class<?>[] cz = null;
        Constructor<?>[] cons = clz.getConstructors();
        for (Constructor<?> ct : cons) {
            Class<?>[] clazz = ct.getParameterTypes();
            if (objList.get(0).length == clazz.length) {
                cz = clazz;
                break;
            }
        }

        List<T> list = new ArrayList<T>();
        for (Object[] obj : objList) {
            Constructor<T> cr = clz.getConstructor(cz);
            list.add(cr.newInstance(obj));
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        // 初始化数据
        Object[] obj = new Object[4];
        obj[0] = "hw";
        obj[1] = BigDecimal.valueOf(13699999999L);
        obj[2] = 4.7;
        obj[3] = 5.0;

        Object[] obj1 = new Object[4];
        obj1[0] = "vivo";
        obj1[1] = BigDecimal.valueOf(13611111111L);
        obj1[2] = 4.8;
        obj1[3] = 5.7;

        List<Object[]> objList = new ArrayList<>();
        objList.add(obj);
        objList.add(obj1);

        // 工具类
        List<PhoneEntity> list = ObjectToBeanUtils.objectToBean(objList, PhoneEntity.class);
        for (PhoneEntity phone : list) {
//            System.out.println(phone.getPlate() + "\t" + phone.getNumber()
//                    + "\t" + phone.getMemory() + "\t" + phone.getSize());
            System.out.println(phone.toString());
        }
    }



}
