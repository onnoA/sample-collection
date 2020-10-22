package com.onnoa.shop.demo.authority.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onnoa.shop.demo.authority.system.domain.SysFrontViewResource;
import com.onnoa.shop.demo.authority.system.dto.AddOrUpdateResourceInfoDto;
import com.onnoa.shop.demo.authority.system.dto.BaseSysFrontViewResourceDto;
import com.onnoa.shop.demo.authority.system.dto.ButtonListDto;
import com.onnoa.shop.demo.authority.system.dto.FrontViewResourceDto;

public interface SysFrontViewResourceService extends IService<SysFrontViewResource> {

    /**
     * 根据用户名获取菜单树形结构
     *
     * @param username 用户名
     * @return 用户权限列表
     */
    List<BaseSysFrontViewResourceDto> getFrontViewResource(String username);

    /**
     * 根据用户名获取菜单树形结构(此接口为java8 新特性实现)
     *
     * @param username 用户名
     * @return 用户权限列表
     */
    List<BaseSysFrontViewResourceDto> getTreeList(String username);

    /**
     * 根据用户名和父id获取按钮列表
     *
     * @param dto 请求参数
     * @return 按钮列表
     */
    List<BaseSysFrontViewResourceDto> getButtonList(ButtonListDto dto);

    /**
     * 获取所有的菜单列表(包括文件夹、文件以及按钮)
     *
     * @return 菜单列表
     */
    List<BaseSysFrontViewResourceDto> getAllFrontResourceList();

    /**
     * 根据id获取前端资源信息
     *
     * @param id id
     * @return 前端资源信息
     */
    FrontViewResourceDto getViewResourceById(String id);

    // todo 待修改
    Boolean modifyResource(FrontViewResourceDto requestDto);

    /**
     * 根据id删除资源信息
     *
     * @param viewId 前端资源id
     * @return 是否成功
     */
    void deleteByViewId(String viewId);

    /**
     * 新增资源文件
     *
     * @param addInfoDto 请求参数
     */
    void addResourceInfo(AddOrUpdateResourceInfoDto addInfoDto);


    /**
     * 修改资源文件
     *
     * @param updateInfoDto 请求参数
     */
    void updateResourceInfo(AddOrUpdateResourceInfoDto updateInfoDto);
}
