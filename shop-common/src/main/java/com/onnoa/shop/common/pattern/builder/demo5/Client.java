package com.onnoa.shop.common.pattern.builder.demo5;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author onnoA
 * @Description
 * @date 2021年06月02日 10:01
 */
public class Client {

    public static void main(String[] args) {
        Course course = new Course.Builder().note("笔记").subjectData("科目资料").subjectName("科目名字").work("家庭作业").build();
        System.out.println(JSON.toJSON(course));
        List<String> list = new ArrayList<>();
        list.add("test1");
        list.add("test2");
        User user = User.builder().password("密码").username("用户名").listTest("abc").listTest("ced").list(list).build();
        System.out.println(user);
//        String s = User.builder().toString();
//        System.out.println(s);
//
//
//        List<String> list1 = new ArrayList<>();
//        list1.add("test1");
//        list1.add("test2");
//        List<String> list2 = Collections.singletonList(list1.get(0));
//        System.out.println(list2);


        String str = "\"[{'4421164': 0.0}, {'8584773': 0.0}]\"";
        String s = str.toString();
        String s1 = JSON.toJSONString(str);
        List list1 = JSON.parseArray(s , List.class);
        System.out.println(list1);
    }
}
