package com.onnoa.shop.common.pattern.builder.demo5;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;
import java.util.Map;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月03日 16:39
 */
public class JsonTest {

    public static void main(String[] args) {
        String json = "[{'4421164': 0.0}, {'8584773': 0.0}]";
        List list = JSON.parseObject(json, List.class);
        System.out.println(list);

        Object obj = "\"[{'4421164': 0.0}, {'8584773': 0.0}]\"";
        String str = "\"[{'4421164': 0.0}, {'8584773': 0.0}]\"";
        String replace1 = json.replace("\"", "");
        String replace = str.replace("\"", "");
        System.out.println(replace1 + "\n" + replace);
//        String s1 = JSONArray.toJSONString(replace);
        List list1 = JSON.parseObject(replace, List.class);
        System.out.println(list1);
//        String s = obj.toString();
//        List list1 = JSON.parseObject(obj.toString(), List.class);
        String s = obj.toString();
//        String s1 = JSONObject.toJSONString(s);
//        System.out.println(s1);
//        List list1 = JSONObject.parseObject(s1, List.class);
//        System.out.println(list1);
//        JSONObject jsonObject = (JSONObject) JSONObject.parse(obj.toString());
//        JSONArray jsonArray = jsonObject.getJSONArray("name");


    }
}
