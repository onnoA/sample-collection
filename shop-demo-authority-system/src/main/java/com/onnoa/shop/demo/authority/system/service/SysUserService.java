package com.onnoa.shop.demo.authority.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;

public interface SysUserService extends IService<SysUser>{

    String login(SysUserLoginDto loginDto);

    VerifyCodeVo getVerifyCode();

}
