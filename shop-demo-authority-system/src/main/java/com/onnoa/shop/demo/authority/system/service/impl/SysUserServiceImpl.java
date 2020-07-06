package com.onnoa.shop.demo.authority.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.common.utils.CreateVerifyCodeUtil;
import com.onnoa.shop.common.utils.IPUtil;
import com.onnoa.shop.common.utils.MD5Util;
import com.onnoa.shop.common.utils.UuidUtil;
import com.onnoa.shop.demo.authority.system.cache.AuthoritySystemCache;
import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.onnoa.shop.demo.authority.system.dto.RedisLoginUserDto;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.exception.UserException;
import com.onnoa.shop.demo.authority.system.mapper.SysUserMapper;
import com.onnoa.shop.demo.authority.system.service.SysUserService;
import com.onnoa.shop.demo.authority.system.vo.LoginVo;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public String login(SysUserLoginDto loginDto) {
        // 校验验证码是否正确
        String verifyCode = (String) AuthoritySystemCache.USER_VERIFY_CODE.get(loginDto.getUuid());
        if (StringUtils.isBlank(verifyCode)) {
            throw UserException.USER_VERIFY_CODE_FAILURE;
        }
        if (!verifyCode.equalsIgnoreCase(loginDto.getVerifyCode())) {
            throw UserException.USER_VERIFY_CODE_ERROR;
        }

        SysUser sysUser = userMapper.selectOne(new QueryWrapper<SysUser>().eq("username", loginDto.getUsername()));
        if (Objects.isNull(sysUser)) {
            throw UserException.USER_NOT_EXITS.format("用户名为:" + loginDto.getUsername());
        }

        // 校验密码是否正确
        boolean isMatch = MD5Util.verify(loginDto.getPassword(), sysUser.getPassword());
        if (!isMatch) {
            throw UserException.USER_PASSWORD_WRONG;
        }

        // 登录成功
        sysUser.setLastLoginIp(IPUtil.getIpAddress());
        sysUser.setLoginCount(sysUser.getLoginCount() + 1);
        sysUser.setUpdateTime(new Date());
        sysUser.setLastLoginTime(new Date());
        userMapper.updateById(sysUser);

        // 生成accessToken
        String accessToken = "";
        RedisLoginUserDto redisLoginUserDto = new RedisLoginUserDto();
        BeanUtils.copyToBeanInFields(sysUser, redisLoginUserDto, Arrays.asList("id", "username", "password"));
        redisLoginUserDto.setAccessToken(accessToken);
        AuthoritySystemCache.USER_ACCESS_TOKEN.set(sysUser.getId(), redisLoginUserDto, 24 * 60 * 60);

        return accessToken;
    }


    @Override
    public VerifyCodeVo getVerifyCode() {
        Object[] image = CreateVerifyCodeUtil.createImage(true);
        String verifyCode = (String) image[0];
        String base64 = (String) image[1];
        String uuid = UuidUtil.genUuid();
        // 设置验证码过期时间为10分钟
        AuthoritySystemCache.USER_VERIFY_CODE.set(uuid, verifyCode, 10 * 60);
        VerifyCodeVo verifyCodeVo = new VerifyCodeVo();
        verifyCodeVo.setCodeBase64(base64);
        verifyCodeVo.setCodeUUID(uuid);
        return verifyCodeVo;
    }
}
