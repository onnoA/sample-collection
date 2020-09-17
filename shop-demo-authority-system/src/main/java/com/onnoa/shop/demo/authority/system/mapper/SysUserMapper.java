package com.onnoa.shop.demo.authority.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.onnoa.shop.demo.authority.system.dto.UserDto;
import com.onnoa.shop.demo.authority.system.dto.UserReqDto;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    List<UserDto> selectUserPage(RowBounds rowBounds, UserReqDto userReqDto);
}