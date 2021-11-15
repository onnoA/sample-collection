package com.onnoa.shop.common.pattern.templatemethod.demo4hook;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author onnoA
 * @date 2021年05月31日 17:33
 */
public class HookTestUtil {

    public static Map<String, Object> hookTest() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("hook", "hook test..");
        return map;
    }
}
