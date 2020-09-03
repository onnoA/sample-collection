package com.onnoa.shop.demo.authority.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResource;
import com.onnoa.shop.demo.authority.system.dto.BaseSysFrontViewResourceDto;
import com.onnoa.shop.demo.authority.system.dto.FrontViewResourceDto;

public interface SysFrontViewResourceMapper extends BaseMapper<SysFrontViewResource> {

    SysFrontViewResource getViewResourcesByRoleIdAndPath(@Param("roleId") String roleId, @Param("frontPath") String frontPath);

    List<BaseSysFrontViewResourceDto> getFrontViewResourceByRoleId(@Param("roleId") String roleId);

    List<SysFrontViewResource> getFrontButtonList(@Param("roleId") String roleId, @Param("parentId") String parentId);

    List<BaseSysFrontViewResourceDto> getAllFrontResourceList();

    FrontViewResourceDto selectViewResourceById(@Param("frontViewId") String frontViewId);

    List<SysFrontViewResource> selectByParentId(@Param("parentId") String parentId);
}
