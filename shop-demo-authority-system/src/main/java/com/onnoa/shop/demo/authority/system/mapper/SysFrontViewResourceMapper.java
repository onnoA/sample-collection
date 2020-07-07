package com.onnoa.shop.demo.authority.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResource;
import org.apache.ibatis.annotations.Param;

public interface SysFrontViewResourceMapper extends BaseMapper<SysFrontViewResource> {

    SysFrontViewResource getViewResourcesByRoleIdAndPath(@Param("roleId") String roleId, @Param("frontPath") String frontPath);
}
