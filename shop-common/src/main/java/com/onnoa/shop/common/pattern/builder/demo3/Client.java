package com.onnoa.shop.common.pattern.builder.demo3;

import com.alibaba.fastjson.JSON;

/**
 * @author onnoA
 * @Description 调用客户端
 * @date 2021年06月01日 20:22
 */
public class Client {

    public static void main(String[] args) {
        CourseBuilder courseBuilder = new CourseBuilder().subjectName("Java 从入门到放弃课程").subjectData("Java 从入门到放弃书籍").note("java 笔记").work("Java 作业");
        Course course = courseBuilder.build();
        System.out.println(JSON.toJSON(course));


    }
}
