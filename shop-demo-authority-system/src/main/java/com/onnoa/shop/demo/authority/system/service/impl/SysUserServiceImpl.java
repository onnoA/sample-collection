package com.onnoa.shop.demo.authority.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.common.utils.CreateVerifyCodeUtil;
import com.onnoa.shop.common.utils.UuidUtil;
import com.onnoa.shop.demo.authority.system.cache.AuthoritySystemCache;
import com.onnoa.shop.demo.authority.system.dto.SysUserLoginDto;
import com.onnoa.shop.demo.authority.system.exception.UserException;
import com.onnoa.shop.demo.authority.system.vo.LoginVo;
import com.onnoa.shop.demo.authority.system.vo.VerifyCodeVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onnoa.shop.demo.authority.system.mapper.SysUserMapper;
import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.onnoa.shop.demo.authority.system.service.SysUserService;
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public LoginVo login(SysUserLoginDto loginDto) {
        // 对密码进行加盐加密

        QueryWrapper<SysUser> qw = new QueryWrapper<>();
        qw.eq("username",loginDto.getUsername()).eq("password",loginDto.getPassword());
        List<SysUser> userList = userMapper.selectList(qw);
        if(CollectionUtils.isEmpty(userList)){
            throw UserException.USER_NOT_EXITS.format("用户名为:"+loginDto.getUsername());
        }
        SysUser sysUser = userList.get(0);
        LoginVo loginVo = new LoginVo();
        BeanUtils.copyToBean(sysUser,loginVo);
        return loginVo;
    }

    @Override
    public VerifyCodeVo getVerifyCode() {
        Object[] image = CreateVerifyCodeUtil.createImage(true);
        String verifyCode = (String) image[0];
        String base64 = (String) image[1];
        String uuid = UuidUtil.genUuid();
        // 设置验证码过期时间为10分钟
        AuthoritySystemCache.USER_VERIFY_CODE.set(uuid,verifyCode,10*60);
        VerifyCodeVo verifyCodeVo = new VerifyCodeVo();
        verifyCodeVo.setCodeBase64(base64);
        verifyCodeVo.setCodeUUID(uuid);
        return verifyCodeVo;
    }
}
