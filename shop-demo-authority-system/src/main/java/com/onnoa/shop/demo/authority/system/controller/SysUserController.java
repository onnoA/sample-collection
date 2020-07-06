package com.onnoa.shop.demo.authority.system.controller;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.service.SysUserService;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @PostMapping(value = "/login")
    public ResultBean login(@RequestBody SysUserLoginDto loginDto) {
        String accessToken = userService.login(loginDto);
        return ResultBean.success(accessToken);
    }


    @GetMapping(value = "/verify")
    public ResultBean getVerifyCode() {
        VerifyCodeVo resultVo = userService.getVerifyCode();
        return ResultBean.success(resultVo);
    }
}
