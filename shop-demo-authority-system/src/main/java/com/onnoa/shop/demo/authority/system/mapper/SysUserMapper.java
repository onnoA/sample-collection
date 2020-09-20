package com.onnoa.shop.demo.authority.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.onnoa.shop.demo.authority.system.domain.SysUser;
import com.onnoa.shop.demo.authority.system.dto.UserDto;
import com.onnoa.shop.demo.authority.system.dto.UserReqDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends BaseMapper<SysUser> {

    List<UserDto> selectUserPage(RowBounds rowBounds, UserReqDto userReqDto);

    List<UserDto> getUserMap(Map map);

    List<UserDto> getUserDto(UserDto dto);

    List<UserDto> getUserStr(@Param("username") String username);
}