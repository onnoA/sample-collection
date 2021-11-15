package cn.quellanan.util;

import cn.quellanan.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: Test
 * @description:
 * @author: onnoA
 * @date: 2021/11/11
 **/
public class Test {
    public static void main(String [] args) {
        User stu1 = new User(01, "19", "张三");
        User stu2 = new User(02, "23", "李四");
        User stu3 = new User(01, "28", "王五");
        List<User> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);
        // 判断学生年龄是否有大于27岁的
        list.stream().anyMatch(student -> student.getId() > 27);
        System.out.println(list);
    }

}
