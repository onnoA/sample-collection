package com.onnoa.shop.demo.authority.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.onnoa.shop.demo.authority.system.dto.AuthDto;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;

public interface SysUserService extends IService<SysUser> {

    /**
     *  登录
     * @param loginDto 登录对象
     * @return accessToken
     */
    String login(SysUserLoginDto loginDto);

    /**
     * 获取验证码
     * @return 验证码对象
     */
    VerifyCodeVo getVerifyCode();

    /**
     *  认证接口
     * @param authDto 认证参数
     * @return 认证是否成功
     */
    Boolean auth(AuthDto authDto);
}
