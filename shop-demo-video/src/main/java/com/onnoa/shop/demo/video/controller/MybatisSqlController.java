package com.onnoa.shop.demo.video.controller;

import com.onnoa.shop.demo.video.common.result.ResultBean;
import com.onnoa.shop.demo.video.service.MybatisSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @className: MybatisSqlController
 * @description:
 * @author: onnoA
 * @date: 2021/9/27
 **/
@RestController
@RequestMapping(value = "/mybatis")
public class MybatisSqlController {

    @Autowired
    private MybatisSqlService mybatisSqlService;

    @PostMapping(value = "/parse")
    public ResultBean parse(@RequestBody Map<String, Object> map){
        Map parse = mybatisSqlService.parse(map);
        return ResultBean.success(parse);


    }

}
