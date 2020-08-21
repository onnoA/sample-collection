package com.onnoa.shop.demo.authority.system.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.demo.authority.system.domain.SysBackEndInterResource;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResource;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResourceBackInterResource;
import com.onnoa.shop.demo.authority.system.domain.SysRole;
import com.onnoa.shop.demo.authority.system.dto.BaseSysFrontViewResourceDto;
import com.onnoa.shop.demo.authority.system.dto.ButtonListDto;
import com.onnoa.shop.demo.authority.system.dto.FrontViewResourceDto;
import com.onnoa.shop.demo.authority.system.enums.FrontViewTypeEnum;
import com.onnoa.shop.demo.authority.system.exception.UserException;
import com.onnoa.shop.demo.authority.system.mapper.SysBackEndInterResourceMapper;
import com.onnoa.shop.demo.authority.system.mapper.SysFrontViewResourceBackInterResourceMapper;
import com.onnoa.shop.demo.authority.system.mapper.SysFrontViewResourceMapper;
import com.onnoa.shop.demo.authority.system.mapper.SysRoleMapper;
import com.onnoa.shop.demo.authority.system.service.SysFrontViewResourceService;
import com.onnoa.shop.demo.authority.system.util.TreeUtil;

@Service
public class SysFrontViewResourceServiceImpl extends ServiceImpl<SysFrontViewResourceMapper, SysFrontViewResource>
    implements SysFrontViewResourceService {

    @Autowired
    private SysFrontViewResourceMapper frontViewResourceMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysFrontViewResourceBackInterResourceMapper frontBackResourceMapper;

    @Autowired
    private SysBackEndInterResourceMapper backEndInterResourceMapper;

    @Override
    public List<BaseSysFrontViewResourceDto> getFrontViewResource(String username) {
        List<SysRole> rolesByUsername = roleMapper.getRolesByUsername(username);
        Set<BaseSysFrontViewResourceDto> tempFrontViewResourceSet = new HashSet<>();
        rolesByUsername.parallelStream().forEach(role -> {
            List<BaseSysFrontViewResourceDto> frontViewResourceList = frontViewResourceMapper
                .getFrontViewResourceByRoleId(role.getId());
            tempFrontViewResourceSet.addAll(frontViewResourceList);
        });

        List<BaseSysFrontViewResourceDto> list = Lists.newArrayList();
        list.addAll(tempFrontViewResourceSet);
        // 递归查询前端资源的父子级关系，返回树形结构
        List<BaseSysFrontViewResourceDto> frontViewResourceList = TreeUtil.findTreeStructList(list);
        return frontViewResourceList;
    }

    @Override
    public List<BaseSysFrontViewResourceDto> getButtonList(ButtonListDto dto) {
        List<SysRole> rolesByUsername = roleMapper.getRolesByUsername(dto.getUsername());
        Set<SysFrontViewResource> tempButtonSet = new HashSet<>();
        rolesByUsername.parallelStream().forEach(role -> {
            List<SysFrontViewResource> buttonList = frontViewResourceMapper.getFrontButtonList(role.getId(),
                dto.getParentId());
            tempButtonSet.addAll(buttonList);
        });

        List<SysFrontViewResource> buttonList = Lists.newArrayList();
        buttonList.addAll(tempButtonSet);
        List<BaseSysFrontViewResourceDto> resultButtonList = Lists.newArrayList();
        BeanUtils.copyBeanList(buttonList, resultButtonList, BaseSysFrontViewResourceDto.class);

        return resultButtonList;
    }

    @Override
    public List<BaseSysFrontViewResourceDto> getAllFrontResourceList() {
        List<BaseSysFrontViewResourceDto> resultList = frontViewResourceMapper.getAllFrontResourceList();
        if (CollectionUtils.isEmpty(resultList)) {
            throw UserException.DATA_INVALID.format("菜单数据未进行初始化，请进行配置。");
        }

        // 构建菜单列表的树形结构
        return TreeUtil.findTreeStructList(resultList);
    }

    @Override
    public FrontViewResourceDto getViewResourceById(String id) {
        if (StringUtils.isBlank(id)) {
            throw UserException.DATA_INVALID.format("菜单id不能为空。");
        }
        FrontViewResourceDto resultDto = frontViewResourceMapper.selectViewResourceById(id);
        if (resultDto == null) {
            throw UserException.DATA_INVALID.format("此菜单信息不存在。");
        }
        SysFrontViewResource parentFrontViewResource = frontViewResourceMapper.selectById(resultDto.getParentId());
        if (parentFrontViewResource != null) {
            resultDto.setParentName(parentFrontViewResource.getName());
        }
        return resultDto;
    }

    @Override
    public Boolean modifyResource(FrontViewResourceDto requestDto) {
        // 按钮
        if (FrontViewTypeEnum.BUTTON.getType().equals(requestDto.getType())) {
            QueryWrapper<SysFrontViewResourceBackInterResource> qw = new QueryWrapper<>();
            qw.eq("front_view_path_id", requestDto.getId());
            SysFrontViewResourceBackInterResource frontBackResource = frontBackResourceMapper.selectOne(qw);
            if (frontBackResource == null) {
                SysBackEndInterResource backEndEntity = new SysBackEndInterResource();
                backEndEntity.setCreateTime(new Date());
                backEndEntity.setUpdateTime(new Date());
                backEndEntity.setInterfaceName(requestDto.getName());
                backEndEntity.setDescr(requestDto.getName());
                backEndEntity.setInterfaceUrl(requestDto.getInterfaceUrl());
                backEndInterResourceMapper.insert(backEndEntity);

                SysFrontViewResourceBackInterResource entity = new SysFrontViewResourceBackInterResource();
                entity.setCreateTime(new Date());
                entity.setUpdateTime(new Date());
                entity.setFrontViewPathId(requestDto.getId());
                entity.setBackEndViewUrlId(backEndEntity.getId());
                // 插入关联表
                frontBackResourceMapper.insert(entity);
            }
            else {
                SysBackEndInterResource entity = new SysBackEndInterResource();
                entity.setId(frontBackResource.getBackEndViewUrlId());
                entity.setInterfaceUrl(requestDto.getInterfaceUrl());
                entity.setInterfaceName(requestDto.getName());
                entity.setDescr(requestDto.getName());
                backEndInterResourceMapper.updateById(entity);
            }
        }
        requestDto.setUpdateTime(new Date());
        SysFrontViewResource entity = new SysFrontViewResource();
        BeanUtils.copyToBean(requestDto, entity);
        return frontViewResourceMapper.updateById(entity) > 0;

    }

    @Override
    public Boolean deleteByViewId(String viewId) {
        // 数据校验
        if (StringUtils.isBlank(viewId)) {
            throw UserException.DATA_INVALID.format("id不能为空，请选择菜单。");
        }


        return null;
    }

}
