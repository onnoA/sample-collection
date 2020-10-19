package com.onnoa.message.controller;

import com.google.common.collect.Maps;
import com.onnoa.message.cache.SmsCache;
import com.onnoa.message.service.impl.IMessageServiceImpl;
import com.onnoa.shop.common.exception.ServiceException;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.common.utils.UuidUtil;
import entity.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/sms")
public class SmsController {

    @Autowired
    private IMessageServiceImpl messageService;

    /**
     * 获取验证码发送手机
     *
     * @param userDto
     * @return
     */
    @PostMapping(value = "/getCode")
    public ResultBean<?> getCode(@RequestBody @Valid UserDto userDto) {
        if (SmsCache.USER_SMS_CODE.get(userDto.getPhoneNumber()) != null) {
            throw ServiceException.SMS_CODE_HAS_NOT_EXPIRED;
        }
        Map<String, Object> codeMap = Maps.newHashMap();
        String code = UuidUtil.genUuid().substring(0, 6);
        codeMap.put("code", code);
        // redis 中验证码的有效期为五分钟
        SmsCache.USER_SMS_CODE.set(userDto.getPhoneNumber(), code, 5 * 60);
        boolean isSuccess = messageService.sendMessage(userDto.getPhoneNumber(), "SMS_203716093", codeMap);
        return ResultBean.success(isSuccess);
    }


    @PostMapping(value = "verify")
    public ResultBean<?> verify(@RequestBody @Valid UserDto userDto) {
        String code = userDto.getCode();
        if (SmsCache.USER_SMS_CODE.get(userDto.getPhoneNumber()) == null) {
            throw ServiceException.SMS_CODE_HAS_EXPIRED;
        }
        String redisCode = SmsCache.USER_SMS_CODE.get(userDto.getPhoneNumber()).toString();
        if (!redisCode.equalsIgnoreCase(code)) {
            throw ServiceException.SMS_CODE_ERROR;
        }

        // 验证码逻辑校验成功，调用用户注册逻辑
        //invokeRegister
        return ResultBean.success();
    }

}
