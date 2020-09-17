package com.onnoa.shop.demo.authority.system.controller;

import javax.validation.Valid;

import com.onnoa.shop.demo.authority.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onnoa.shop.common.result.ResultBean;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/sys/role")
@Api(tags = "角色权限相关接口", description = "提供角色权限相关的 Rest API")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @PostMapping(value = "/login")
    @ApiOperation(value = "用户登录接口", notes = "登录接口")
    public ResultBean<String> login(@RequestBody @Valid SysUserLoginDto loginDto) {
        //String accessToken = roleService.login(loginDto);
        return ResultBean.success();
    }

}
