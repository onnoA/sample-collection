package com.onnoa.myshop.springcloud.provider.after.test.controller;

import com.onnoa.myshop.springcloud.provider.after.test.service.MessageFeignService;
import com.onnoa.shop.common.result.ResultBean;
import entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sms")
public class SmsController {

    @Autowired
    private MessageFeignService smsFeignService;

    @PostMapping(value = "/getCode")
    public ResultBean getCode(@RequestBody @Valid UserDto userDto){
        return smsFeignService.getCode(userDto);
    }

}
