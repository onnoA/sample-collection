package com.onnoa.shop.demo.authority.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.common.utils.CreateVerifyCodeUtil;
import com.onnoa.shop.common.utils.IPUtil;
import com.onnoa.shop.common.utils.MD5Util;
import com.onnoa.shop.common.utils.UuidUtil;
import com.onnoa.shop.common.utils.jwt.JWTConstant;
import com.onnoa.shop.common.utils.jwt.JWtObj;
import com.onnoa.shop.common.utils.jwt.JwtTokenUtils2;
import com.onnoa.shop.demo.authority.system.cache.AuthoritySystemCache;
import com.onnoa.shop.demo.authority.system.domain.SysBackEndInterResource;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResource;
import com.onnoa.shop.demo.authority.system.domain.SysRole;
import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.onnoa.shop.demo.authority.system.dto.AuthDto;
import com.onnoa.shop.demo.authority.system.dto.RedisLoginUserDto;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.exception.UserException;
import com.onnoa.shop.demo.authority.system.mapper.SysBackEndInterResourceMapper;
import com.onnoa.shop.demo.authority.system.mapper.SysFrontViewResourceMapper;
import com.onnoa.shop.demo.authority.system.mapper.SysRoleMapper;
import com.onnoa.shop.demo.authority.system.mapper.SysUserMapper;
import com.onnoa.shop.demo.authority.system.service.SysUserService;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysFrontViewResourceMapper frontViewResourceMapper;
    @Autowired
    private SysBackEndInterResourceMapper backEndInterResourceMapper;

    @Override
    public String login(SysUserLoginDto loginDto) {
        // 校验验证码是否正确
        String verifyCode = (String) AuthoritySystemCache.USER_VERIFY_CODE.get(loginDto.getCodeUUID());
        if (StringUtils.isBlank(verifyCode)) {
            throw UserException.USER_VERIFY_CODE_FAILURE;
        }
        if (!verifyCode.equalsIgnoreCase(loginDto.getVerifyCode())) {
            throw UserException.USER_VERIFY_CODE_ERROR;
        }

        SysUser sysUser = userMapper.selectOne(new QueryWrapper<SysUser>().eq("username", loginDto.getUsername()));
        if (Objects.isNull(sysUser)) {
            throw UserException.USER_NOT_EXITS.format(loginDto.getUsername());
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

        // 签发 accessToken
        JWtObj jWtObj = new JWtObj();
        BeanUtils.copyToBean(sysUser, jWtObj);
        // 一天过期
        String accessToken = JwtTokenUtils2.createJWT(jWtObj, UuidUtil.genUuid(), JSONObject.toJSONString(jWtObj), JWTConstant.ACCESS_TOKEN_SECRET, 24 * 60 * 60 * 1000);
        RedisLoginUserDto redisLoginDto = new RedisLoginUserDto();
        BeanUtils.copyToBeanInFields(sysUser, redisLoginDto, Arrays.asList("id", "username", "password"));
        redisLoginDto.setAccessToken(accessToken);
        redisLoginDto.setRefTime(JwtTokenUtils2.getTokenFailureTime(24 * 60));
        boolean boo = AuthoritySystemCache.USER_ACCESS_TOKEN.set(sysUser.getId(), redisLoginDto, 24 * 60 * 60);
        log.info("boo:{}", boo);

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

    @Override
    public Boolean auth(AuthDto authDto) {
        if (authDto.getFrontPath().equals("/")) {
            return true;
        }
        List<SysRole> roleList = sysRoleMapper.getRolesByUsername(authDto.getUsername());
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (SysRole role : roleList) {
                SysFrontViewResource frontViewResource = frontViewResourceMapper.getViewResourcesByRoleIdAndPath(role.getId(), authDto.getFrontPath());
                if (frontViewResource != null) {
                    SysBackEndInterResource backEndInterResource = backEndInterResourceMapper.getBackEndInterResourceByFrontId(frontViewResource.getId());
                    if (backEndInterResource != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
