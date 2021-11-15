package com.onnoa.shop.demo.authority.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.authority.system.annotation.NoNeedTokenAuth;
import com.onnoa.shop.demo.authority.system.dto.PacketCheckDto;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author onnoA 分组校验测试控制层
 * @date 2021年05月24日 10:49
 */
@RestController
@RequestMapping(value = "/packet/check")
public class PacketCheckController {

    @PostMapping(value = "/add")
    @NoNeedTokenAuth
    public ResultBean addTest(@RequestBody @Validated({PacketCheckDto.Add.class}) PacketCheckDto packetCheckDto, BindingResult addBindingResult) {
        System.out.println(JSONObject.toJSON(packetCheckDto));
        StringBuffer sb = new StringBuffer();
        List<FieldError> fieldErrors = addBindingResult.getFieldErrors();
        if (CollectionUtil.isNotEmpty(fieldErrors)) {
            for (FieldError fieldError : addBindingResult.getFieldErrors()) {
                sb.append("'").append(fieldError.getField()).append("': ").append(fieldError.getDefaultMessage()).append("\n");
            }
            return ResultBean.error(sb.deleteCharAt(sb.length() - 1));
        }
        return ResultBean.success();
    }

    @PostMapping(value = "/update")
    @NoNeedTokenAuth
    public ResultBean updateTest(@RequestBody Map packetCheckDto) {
        System.out.println(JSONObject.toJSON(packetCheckDto));
        return ResultBean.success();
    }
}
