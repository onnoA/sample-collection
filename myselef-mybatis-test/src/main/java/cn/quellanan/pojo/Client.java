package cn.quellanan.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @className: Client
 * @description:
 * @author: onnoA
 * @date: 2021/11/1
 **/
public class Client {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Mapper mapper = new Mapper();
        MybatisXmlConfig xmlConfig = new MybatisXmlConfig();
        xmlConfig.setNamespace("test");
        xmlConfig.setMapperiname("TestDao");
        mapper.setParmType(MybatisXmlConfig.class);
        Class<?> parmType = mapper.getParmType();

        Field declaredField = parmType.getDeclaredField("namespace");
        System.out.println(declaredField);
        declaredField.setAccessible(true);

        Object o = declaredField.get(xmlConfig);
        System.out.println(o.toString());
//        Object o = parameter[0];
//        value = declaredField.get(o);
    }

    /*public static void main(String[] args) {
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("5");
        list1.add("6");

        List<String> list2 = new ArrayList<String>();
        list2.add("2");
        list2.add("3");
        list2.add("7");
        list2.add("8");

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
        System.out.println("---差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out :: println);

    }*/
}
