package com.onnoa.java8.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: StreamTest
 * @description:
 * @author: onnoA
 * @date: 2021/11/19
 **/
public class StreamTest {

    public static void main(String[] args) {
        List<Map> list = new ArrayList<Map>();
        for (int i = 0; i < 10; i++) {
            Map map = new HashMap();
            map.put("id", i);
            map.put("name", "张" + i);
            map.put("code", 10 + i);
            list.add(map);
        }
        for (int i = 0; i < 10; i++) {
            Map map = new HashMap();
            map.put("id", i);
            map.put("name", "张" + i);
            map.put("code", 10 + i);
            list.add(map);
        }
        //List stream 按 Map 某个 key 合计 value 值
        int totalCode = list.stream().mapToInt(m -> (int) m.get("code")).sum();
        System.out.println("totalCode = " + totalCode);
        //List stream 按 Map 中某个 key 分组
        Map<String, List<Map>> map = list.stream().collect(Collectors.groupingBy(
                (Map m) -> (String)m.get("name"))
        );

        System.out.println(map);



    }
}
