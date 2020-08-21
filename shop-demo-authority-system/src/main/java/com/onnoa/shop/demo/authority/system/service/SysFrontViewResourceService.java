package com.onnoa.shop.demo.authority.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResource;
import com.onnoa.shop.demo.authority.system.dto.ButtonListDto;
import com.onnoa.shop.demo.authority.system.dto.BaseSysFrontViewResourceDto;
import com.onnoa.shop.demo.authority.system.dto.FrontViewResourceDto;

import java.util.List;

public interface SysFrontViewResourceService extends IService<SysFrontViewResource> {

    List<BaseSysFrontViewResourceDto> getFrontViewResource(String username);

    List<BaseSysFrontViewResourceDto> getButtonList(ButtonListDto dto);

    List<BaseSysFrontViewResourceDto> getAllFrontResourceList();

    FrontViewResourceDto getViewResourceById(String id);

    Boolean modifyResource(FrontViewResourceDto requestDto);

    Boolean deleteByViewId(String viewId);
}
