package com.onnoa.myshop.springcloud.provider.after.test.service;

import com.onnoa.shop.common.result.ResultBean;
import entity.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "message-service")
public interface MessageFeignService {

    @PostMapping(value = "/sms/getCode")
    ResultBean getCode(@RequestBody @Valid UserDto userDto);


}
