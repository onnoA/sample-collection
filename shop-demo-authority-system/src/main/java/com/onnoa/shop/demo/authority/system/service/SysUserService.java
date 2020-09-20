package com.onnoa.shop.demo.authority.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onnoa.shop.common.dto.PageDto;
import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.onnoa.shop.demo.authority.system.dto.AuthDto;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.dto.UserDto;
import com.onnoa.shop.demo.authority.system.dto.UserReqDto;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {

    /**
     * 登录
     * 
     * @param loginDto 登录对象
     * @return accessToken
     */
    String login(SysUserLoginDto loginDto);

    /**
     * 获取验证码
     * 
     * @return 验证码对象
     */
    VerifyCodeVo getVerifyCode();

    /**
     * 认证接口
     * 
     * @param authDto 认证参数
     * @return 认证是否成功
     */
    Boolean auth(AuthDto authDto);

    /**
     * 用户列表
     * 
     * @param userReqDto 请求参数
     * @return 用户列表
     */
    PageDto<UserDto> findUserList(UserReqDto userReqDto);

    void async(int time);

    List<UserDto> getUserMap(Map map);

    List<UserDto> getUserDto(UserDto dto);

    List<UserDto> getUserStr(String username);
}
