package com.onnoa.shop.demo.elk.controller;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.elk.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description:
 * @Author: onnoA
 * @Date: 2020/5/20 17:17
 */
@RestController
@Api(tags = "ELKController", description = "ELK日志搭建案例")
@RequestMapping(value = "/elk")
public class ELKController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @PostMapping(value = "/test")
    @ApiOperation(value = "elk日志测试控制层方法")
    public ResultBean elk(@RequestParam String elk) {

        return ResultBean.success(elk);
    }


    @PostMapping(value = "/path")
    @ApiOperation(value = "elk日志测试控制层方法")
    public ResultBean path(@RequestBody String name, @RequestParam String value) {

        return ResultBean.success(name+value);
    }

    @PostMapping(value = "/validation")
    @ApiOperation(value = "elk日志测试控制层方法")
    public ResultBean validation(@RequestBody User user) {
        return ResultBean.success(user);
    }

    @PostMapping(value = "/an")
    @ApiOperation(value = "elk日志测试控制层方法")
    public ResultBean an() {
        System.out.println(picUtil.getRequestUrl());
        return ResultBean.success(null);
    }

}
