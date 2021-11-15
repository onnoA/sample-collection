package com.onnoa.myshop.springcloud.provider.after.test.controller;

import com.onnoa.myshop.springcloud.provider.after.test.entity.UserInfoDto;
import com.onnoa.myshop.springcloud.provider.after.test.service.TestInvokeService;
import com.onnoa.shop.common.component.AspectLogOperation;
import com.onnoa.shop.common.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestInvokeService invokeService;

    @Value("${test.value}")
    private String value;


    @PostMapping(value = "/nacos")
    @AspectLogOperation
    public ResultBean<?> nacos (){
        return ResultBean.success(value);
    }

    @PostMapping(value = "invoke")
    public ResultBean<?> invoke(@RequestBody UserInfoDto userDto){
        List<UserInfoDto> dtoList = invokeService.testInvoke();
        return ResultBean.success(dtoList);

    }
}
