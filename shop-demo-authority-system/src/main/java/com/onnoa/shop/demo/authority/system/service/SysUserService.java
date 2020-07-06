package com.onnoa.shop.demo.authority.system.service;

import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.vo.LoginVo;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;

public interface SysUserService extends IService<SysUser>{


    LoginVo login(SysUserLoginDto loginDto);

    VerifyCodeVo getVerifyCode();

}
