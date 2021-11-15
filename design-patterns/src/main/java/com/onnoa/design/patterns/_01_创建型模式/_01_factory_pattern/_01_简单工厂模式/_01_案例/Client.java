package com.onnoa.design.patterns._01_创建型模式._01_factory_pattern._01_简单工厂模式._01_案例;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @className: Client
 * @description:
 * @author: onnoA
 * @date: 2021/9/16
 **/
public class Client {

    public static void main(String[] args) {

        IPhoneProduct phoneByReflect = PhoneProducesFactory.getPhoneByReflect(PhoneEnums.HUAWEI.getType());
        System.out.println(phoneByReflect.phoneName());

        System.out.println("======================分割线===============================");

        IPhoneProduct phone = PhoneProducesFactory.getPhone(PhoneEnums.XIAOMI.getType());
        String phoneName = Optional.ofNullable(phone).orElseThrow(() -> new RuntimeException("该手机品牌不存在...")).phoneName();
        System.out.println(phoneName);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> childMap = new HashMap<>();
        Map<String, Object> childMap2 = new HashMap<>();
        childMap2.put("test", "tt");
        childMap.put("str", "result");
        childMap.put("grandson",childMap2);
        map.put("obj","stu");
        map.put("child", childMap);
        Optional<Object> child = Optional.ofNullable(map).map(parent -> parent.get("child"));
        System.out.println(child);



    }
}
