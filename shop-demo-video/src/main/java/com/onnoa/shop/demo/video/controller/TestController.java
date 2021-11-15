package com.onnoa.shop.demo.video.controller;

/**
 * @author onnoA
 * @Description
 * @date 2021年07月16日 11:01
 */
public class TestController {

    public static void main(String[] args) {
        try {
            String target = "www.bai";
            boolean contains = "www.baidu.com".contains(target);
            System.out.println(contains);
            throw new RuntimeException();
        } finally {

        }


    }
}
