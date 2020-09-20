package com.onnoa.shop.demo.authority.system.controller;

import com.onnoa.shop.demo.authority.system.dto.UserDto;
import com.onnoa.shop.demo.authority.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onnoa.shop.common.result.ResultBean;

import java.util.Map;

@RestController
@RequestMapping(value = "sql")
public class SlowSqlController {

    @Autowired
    private SysUserService userService;

    @PostMapping("/map")
    public ResultBean map(@RequestBody Map map){
        return ResultBean.success(userService.getUserMap(map));
    }

    @PostMapping("/dto")
    public ResultBean dto(@RequestBody UserDto dto){
        return ResultBean.success(userService.getUserDto(dto));
    }

    @PostMapping("/str")
    public ResultBean str(@RequestParam String username){
        return ResultBean.success(userService.getUserStr(username));
    }


}
