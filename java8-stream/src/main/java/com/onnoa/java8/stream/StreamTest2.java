package com.onnoa.java8.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @className: StreamTest2
 * @description:
 * @author: onnoA
 * @date: 2021/11/19
 **/
public class StreamTest2 {



    public static void main(String[] args) {
        User user1 = new User("zhangsan", "beijing", 10);

        User user2 = new User("zhangsan", "beijing", 20);
        User user4 = new User("wangwu", "beijing", 20);

        User user3 = new User("lisi", "shanghai", 30);

        List list = new ArrayList();

        list.add(user1);

        list.add(user2);

        list.add(user3);

        list.add(user4);

        Map collect1 = (Map) list.stream().collect(Collectors.groupingBy(User::getUsername));
        System.out.println(collect1);

        Map collect = (Map) list.stream().collect(Collectors.groupingBy(User::getAddress, Collectors.groupingBy(User::getUsername)));

        System.out.println(collect);

    }
}
