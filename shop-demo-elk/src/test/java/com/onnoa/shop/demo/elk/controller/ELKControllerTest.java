package com.onnoa.shop.demo.elk.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/21 19:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ELKControllerTest.class)
class ELKControllerTest {

    @Test
    public void elk(){
        System.out.println("单元测试类");
    }
}
