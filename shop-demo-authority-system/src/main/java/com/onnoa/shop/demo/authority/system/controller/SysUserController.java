package com.onnoa.shop.demo.authority.system.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onnoa.shop.common.component.AspectLogOperation;
import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.authority.system.annotation.NoNeedTokenAuth;
import com.onnoa.shop.demo.authority.system.dto.AuthDto;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.service.SysUserService;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "用户相关接口", description = "提供用户相关的 Rest API，如登录、获取验证码等")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @PostMapping(value = "/login")
    @NoNeedTokenAuth
    @ApiOperation(value = "用户登录接口", notes = "登录接口")
    public ResultBean<String> login(@RequestBody @Valid SysUserLoginDto loginDto) {
        String accessToken = userService.login(loginDto);
        return ResultBean.success(accessToken);
    }

    @GetMapping(value = "/verify")
    @NoNeedTokenAuth
    @AspectLogOperation(option = "获取验证码操作")
    @ApiOperation(value = "登录获取验证码接口", notes = "验证码接口")
    public ResultBean<VerifyCodeVo> getVerifyCode() {
        VerifyCodeVo resultVo = userService.getVerifyCode();
        return ResultBean.success(resultVo);
    }

    @PostMapping(value = "/auth")
    public ResultBean<Boolean> auth(@RequestBody @Valid AuthDto authDto) {
        Boolean hasAuth = userService.auth(authDto);
        return ResultBean.success(hasAuth);
    }

}
