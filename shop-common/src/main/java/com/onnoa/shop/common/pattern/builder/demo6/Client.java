package com.onnoa.shop.common.pattern.builder.demo6;

import java.util.Arrays;

/**
 * @className: Client
 * @description:
 * @author: onnoA
 * @date: 2021/9/23
 **/
public class Client {

    public static void main(String[] args) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.addAscOrder("age")
                .andEqual("addr", "HeBei")
                .andLike("name", "C语言中文网")
                .andGreaterEqual("age", 18);
        QueryRuleSqlBuilder builder = new QueryRuleSqlBuilder(queryRule);
        System.out.println(builder.builder("t_member"));
        System.out.println("Params: " + Arrays.toString(builder.getValues()));
    }
}
