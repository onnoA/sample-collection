package com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例;

import java.util.Objects;
import java.util.Optional;

/**
 * @className: CreateProductFactory
 * @description: 简单工厂设计模式 ： 手机生产简单工厂类
 * @author: onnoA
 * @date: 2021/9/15
 **/
public class PhoneProducesFactory {

    /***
     * @description: 工厂类静态方法
     * @param: phoneType 手机类型
     * @return: com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例.IPhoneProduct
     * @author: onnoA
     * @date: 2021/9/16
     */
    public static IPhoneProduct getPhone(String phoneType) {
        PhoneEnums phoneEnums = PhoneEnums.getByPhoneType(phoneType);
        if(Objects.isNull(phoneEnums)){
            throw new RuntimeException("该" + phoneType + "手机类型无法匹配到相应的手机品牌");
        }
        switch (phoneEnums) {
            case HUAWEI:
                return new HuaWeiPhone();
            case XIAOMI:
                return new XiaoMiPhone();
            default:
                break;
        }
        return null;
    }

    /***
     * @description: 通过全类名反射获取对象
     * @param: phoneEnums
     * @return: com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例.IPhoneProduct
     * @author: onnoA
     * @date: 2021/9/16
     */
    public static IPhoneProduct getPhoneByReflect(String phoneType) {
        PhoneEnums phoneEnums = PhoneEnums.getByPhoneType(phoneType);
        if(Objects.isNull(phoneEnums)){
            throw new RuntimeException("该" + phoneEnums + "手机类型无法匹配到相应的手机品牌");
        }

        // 通过反射获取对象
        IPhoneProduct phoneProduct = null;
        try {
            Class<?> clazz = Class.forName(phoneEnums.getClassName());
            phoneProduct=  (IPhoneProduct) clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return phoneProduct;
    }


}
