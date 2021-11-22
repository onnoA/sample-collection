package com.onnoa.java8.stream.distinct;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @className: DistinctTest
 * @description:
 * @author: onnoA
 * @date: 2021/11/22
 **/
public class DistinctTest {

    public static void main(String[] args) {

        // 列表包对象的形式
        List<Book> list = new ArrayList<>();
        {
            list.add(new Book("Core Java", 100));
            list.add(new Book("Core Java", 200));
            list.add(new Book("Learning Freemarker", 150));
            list.add(new Book("Spring MVC", 300));
            list.add(new Book("Spring MVC", 300));
        }
        long l = list.stream().distinct().count();
        System.out.println("No. of distinct books:" + l);
        list.stream().distinct().forEach(b -> System.out.println(b.getName() + "," + b.getPrice()));

        System.out.println("===============================");

        list.stream().filter(distinctByKey(Book::getName))
                .forEach(b -> System.out.println(b.getName() + "," + b.getPrice()));


        // 列表包Map的形式

        List<Map<String, Object>> listMap = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Map<String, Object> map = new HashMap<String, Object>() {{
                if (finalI / 3 == 0) {
                    put("Name", "June" + finalI);
                    put("QQ", "2572073701");
                } else {
                    put("Name", "June");
                    put("QQ", "2572073701");
                }
            }};
            listMap.add(map);
        }

        // 去重前
        System.out.println("去重前：" + listMap);

        List<Map<String, Object>> list2 = listMap.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(m -> m.get("Name").toString()))
                ), ArrayList::new)
        );

        System.out.println("根据 Name 属性去重后：" + list2);


    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
