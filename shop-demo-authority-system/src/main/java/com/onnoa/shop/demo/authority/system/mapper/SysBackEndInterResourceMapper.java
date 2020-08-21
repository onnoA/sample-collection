package com.onnoa.shop.demo.authority.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onnoa.shop.demo.authority.system.domain.SysBackEndInterResource;
import org.apache.ibatis.annotations.Param;

public interface SysBackEndInterResourceMapper extends BaseMapper<SysBackEndInterResource> {

    SysBackEndInterResource getBackEndInterResourceByFrontId(@Param("frontViewPathId") String frontViewPathId);
}
