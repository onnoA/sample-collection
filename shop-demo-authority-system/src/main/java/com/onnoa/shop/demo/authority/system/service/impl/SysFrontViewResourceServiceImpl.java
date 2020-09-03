package com.onnoa.shop.demo.authority.system.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onnoa.shop.common.utils.BeanUtils;
import com.onnoa.shop.demo.authority.system.domain.SysBackEndInterResource;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResource;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResourceBackInterResource;
import com.onnoa.shop.demo.authority.system.domain.SysRole;
import com.onnoa.shop.demo.authority.system.dto.AddOrUpdateResourceInfoDto;
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

    private static Logger LOGGER = LoggerFactory.getLogger(SysFrontViewResourceService.class);

    @Autowired
    private SysFrontViewResourceMapper frontViewResourceMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysFrontViewResourceBackInterResourceMapper frontBackResourceMapper;

    @Autowired
    private SysBackEndInterResourceMapper backEndInterResourceMapper;

    @Autowired
    private SysFrontViewResourceMapper sysFrontViewResourceMapper;

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
    public void deleteByViewId(String viewId) {
        // 数据校验
        if (StringUtils.isBlank(viewId)) {
            throw UserException.DATA_INVALID.format("id不能为空，请选择相应的菜单。");
        }

        SysFrontViewResource frontViewResource = frontViewResourceMapper.selectById(viewId);
        Assert.notNull(frontViewResource, "该菜单信息不存在。");

        // 删除自身
        delSelfAndButtonInfo(frontViewResource);
        // 递归删除子节点
        recursiveRemoveChildNode(viewId);
    }

    /**
     * 递归删除子节点
     * 
     * @param parentId 父id
     */
    private void recursiveRemoveChildNode(String parentId) {
        List<SysFrontViewResource> viewResourceList = frontViewResourceMapper.selectByParentId(parentId);
        if (CollectionUtils.isNotEmpty(viewResourceList)) {
            for (SysFrontViewResource viewResource : viewResourceList) {
                delSelfAndButtonInfo(viewResource);
                // 调用自身，递归删除子节点
                recursiveRemoveChildNode(viewResource.getId());
            }
        }
    }

    /**
     * 删除前端资源信息以及如果是按钮同时删除按钮后端资源信息以及关联信息
     * 
     * @param viewResource 前端资源信息
     */
    void delSelfAndButtonInfo(SysFrontViewResource viewResource) {
        // 删除前端资源自身
        frontViewResourceMapper.deleteById(viewResource.getId());
        // 按钮，删除关联信息以及后端资源
        if (FrontViewTypeEnum.BUTTON.getType().equals(viewResource.getType())) {
            SysFrontViewResourceBackInterResource frontViewEntity = getFrontViewEntity(viewResource.getId());
            frontBackResourceMapper.deleteById(frontViewEntity.getId());
            backEndInterResourceMapper.deleteById(frontViewEntity.getBackEndViewUrlId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addResourceInfo(AddOrUpdateResourceInfoDto addInfoDto) {
        // 参数校验
        paramsValidation(addInfoDto);

        // 新增前端资源信息
        SysFrontViewResource frontViewResource = new SysFrontViewResource();
        frontViewResource.setCreateTime(new Date());
        frontViewResource.setDescr(addInfoDto.getName());
        BeanUtils.copyToBean(addInfoDto, frontViewResource);
        LOGGER.info("新增前端资源入参:{}", frontViewResource);
        sysFrontViewResourceMapper.insert(frontViewResource);

        // 根据类型 按钮则关联接口权限
        if (FrontViewTypeEnum.BUTTON.getType().equals(addInfoDto.getType())) {
            // 后端资源入库
            SysBackEndInterResource backEndInterResource = new SysBackEndInterResource();
            backEndInterResource.setCreateTime(new Date());
            backEndInterResource.setDescr(addInfoDto.getName());
            backEndInterResource.setInterfaceName(addInfoDto.getName());
            BeanUtils.copyToBean(addInfoDto, backEndInterResource);
            backEndInterResourceMapper.insert(backEndInterResource);
            // 关联表入库
            SysFrontViewResourceBackInterResource frontBackRelation = new SysFrontViewResourceBackInterResource();
            frontBackRelation.setCreateTime(new Date());
            // 后端url id
            frontBackRelation.setBackEndViewUrlId(backEndInterResource.getId());
            // 前端id
            frontBackRelation.setFrontViewPathId(frontViewResource.getId());
            LOGGER.info("关联表构造对象:{},后端资源对象:{}", frontBackRelation, frontViewResource);
            frontBackResourceMapper.insert(frontBackRelation);
        }
    }

    @Override
    public void updateResourceInfo(AddOrUpdateResourceInfoDto updateInfoDto) {
        if (StringUtils.isBlank(updateInfoDto.getViewId())) {
            throw UserException.DATA_INVALID.format("菜单id不能为空。");
        }
        // 参数校验
        paramsValidation(updateInfoDto);

        SysFrontViewResource frontViewResource = new SysFrontViewResource();
        frontViewResource.setId(updateInfoDto.getViewId());
        frontViewResource.setUpdateTime(new Date());
        frontViewResource.setDescr(updateInfoDto.getName());
        BeanUtils.copyToBean(updateInfoDto, frontViewResource);
        sysFrontViewResourceMapper.updateById(frontViewResource);

        // 按钮，更新后端资源信息
        if (FrontViewTypeEnum.BUTTON.getType().equals(updateInfoDto.getType())) {
            SysFrontViewResourceBackInterResource frontViewEntity = getFrontViewEntity(updateInfoDto.getViewId());
            // 更新关联表信息
            frontViewEntity.setUpdateTime(new Date());
            frontBackResourceMapper.updateById(frontViewEntity);

            // 更新后端资源信息
            SysBackEndInterResource backEndInterResource = new SysBackEndInterResource();
            backEndInterResource.setId(frontViewEntity.getBackEndViewUrlId());
            backEndInterResource.setUpdateTime(new Date());
            backEndInterResource.setDescr(updateInfoDto.getName());
            backEndInterResource.setInterfaceName(updateInfoDto.getName());
            backEndInterResource.setInterfaceUrl(updateInfoDto.getInterfaceUrl());
            backEndInterResourceMapper.updateById(backEndInterResource);
        }
    }

    /**
     * 根据前端资源id获取资源关联信息
     * 
     * @param viewId 前端资源id
     * @return 资源关联信息
     */
    SysFrontViewResourceBackInterResource getFrontViewEntity(String viewId) {
        SysFrontViewResourceBackInterResource frontViewEntity = frontBackResourceMapper.selectOne(
            new QueryWrapper<>(new SysFrontViewResourceBackInterResource()).eq("front_view_path_id", viewId));
        if (frontViewEntity == null) {
            throw UserException.OBJECT_IS_NULL;
        }
        return frontViewEntity;
    }

    /**
     * 参数校验方法
     * 
     * @param addInfoDto 新增参数
     */
    private void paramsValidation(AddOrUpdateResourceInfoDto addInfoDto) {
        if (!FrontViewTypeEnum.FOLDER.getType().equals(addInfoDto.getType())) {
            // 菜单文件或按钮
            if (StringUtils.isBlank(addInfoDto.getPath())) {
                throw UserException.DATA_INVALID.format("前端访问路径不能为空。");
            }
            // 按钮
            if (FrontViewTypeEnum.BUTTON.getType().equals(addInfoDto.getType())
                && StringUtils.isBlank(addInfoDto.getInterfaceUrl())) {
                throw UserException.DATA_INVALID.format("后端访问路径不能为空。");
            }
        }
    }

}
