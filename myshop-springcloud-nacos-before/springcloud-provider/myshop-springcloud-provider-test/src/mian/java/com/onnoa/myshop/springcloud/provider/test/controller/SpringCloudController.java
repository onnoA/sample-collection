package com.onnoa.myshop.springcloud.provider.test.controller;

import com.onnoa.shop.common.result.ResultBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/springcloud")
public class SpringCloudController {

    @PostMapping(value = "/test")
    public ResultBean test(@RequestBody Map map){
        return ResultBean.success(map);
    }


}
