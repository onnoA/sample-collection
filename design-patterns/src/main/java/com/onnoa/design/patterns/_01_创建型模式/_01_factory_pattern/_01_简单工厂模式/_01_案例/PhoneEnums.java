package com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @className: PhoneEnums
 * @description: 手机品种枚举类，对各个品类的手机进行统一管理
 * @author: onnoA
 * @date: 2021/9/15
 **/
@Getter
@AllArgsConstructor
public enum PhoneEnums {

    HUAWEI("华为", "huawei", "com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例.HuaWeiPhone"),
//    HUAWEI1("华为", "huawei"),
    XIAOMI("小米", "xiaomi", "com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例.XiaoMiPhone"),
    ;

    // 手机名称
    private String name;

    // 手机产品类型
    private String type;

    // 对应的对象的类路径
    private String className;


    /***
     * @description: 根据手机产品类型获取相应的手机产品
     * @param: phoneType 手机产品类型
     * @return: com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例.PhoneEnums
     * @author: onnoA
     * @date: 2021/9/16
     */
    public static PhoneEnums getByPhoneType(String phoneType){
        return Stream.of(PhoneEnums.values()).filter(phoneEnums -> phoneEnums.type.equalsIgnoreCase(phoneType)).findFirst().orElseGet(() -> null);
    }


    public static Optional<PhoneEnums> getByPhoneTypeJava8(String phoneType){
//        Stream.of(PhoneEnums.values()).filter(phoneEnums -> phoneEnums.type.equalsIgnoreCase(phoneType))
        Optional<Stream<PhoneEnums>> phoneEnumsStream = Optional.ofNullable(Stream.of(PhoneEnums.values()).filter(phoneEnums -> phoneEnums.type.equalsIgnoreCase(phoneType)));

        return Optional.empty();
    }

    public static void main(String[] args) {

    }


}
